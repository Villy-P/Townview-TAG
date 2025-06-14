using System.Collections.Generic;
using UnityEngine;

namespace Enums {
    public enum TreeGenerationStyle {
        PERLIN,
        POINTS
    };
}

public class TreeGeneration : MonoBehaviour {
    public GameObject[] trees;
    public GameObject[] trees2;
    public Enums.TreeGenerationStyle treeGenerationStyle;

    public int tree1Min;
    public int tree1Max;

    public int tree2Min;
    public int tree2Max;

    public int tree1OffsetMin;
    public int tree1OffsetMax;

    public int tree2OffsetMax;
    public int tree2OffsetMin;
    
    public float renderDistance;
    public float tree1Threshold;
    public float tree2Threshold;

    public void GenerateTree1(System.Random r, int x, int z, Terrain t) {
        int tree1Count = r.Next(this.tree1Min, this.tree1Max);
        for (int i = 0; i < tree1Count; i++) {
            float xOff = (float)(this.tree1OffsetMin + (this.tree1OffsetMax - this.tree1OffsetMin) * r.NextDouble());
            float zOff = (float)(this.tree1OffsetMin + (this.tree1OffsetMax - this.tree1OffsetMin) * r.NextDouble());

            Vector3 worldPos = new((x + xOff) / t.terrainData.size.x, 0, (z + zOff) / t.terrainData.size.z);
            int modelIndex = r.Next(0, this.trees.Length - 1);
            TreeInstance instance = new() {
                prototypeIndex = modelIndex,
                position = worldPos,
                widthScale = 1f,
                heightScale = 1f,
                color = Color.white,
                lightmapColor = Color.white
            };
            t.AddTreeInstance(instance);
            t.Flush();
        }
    }

    public void GenerateTree2(System.Random r, int x, int z, Terrain t) {
        int tree2Count = r.Next(this.tree2Min, this.tree2Max);
        for (int i = 0; i < tree2Count; i++) {
            float xOff = (float)(this.tree2OffsetMin + (this.tree2OffsetMax - this.tree2OffsetMin) * r.NextDouble());
            float zOff = (float)(this.tree2OffsetMin + (this.tree2OffsetMax - this.tree2OffsetMin) * r.NextDouble());

            Vector3 worldPos = new((x + xOff) / t.terrainData.size.x, 0, (z + zOff) / t.terrainData.size.z);
            worldPos.y = t.SampleHeight(worldPos);
            int modelIndex = r.Next(0, this.trees2.Length - 1) + this.trees.Length;
            TreeInstance instance = new() {
                prototypeIndex = modelIndex,
                position = worldPos,
                widthScale = 1f,
                heightScale = 1f,
                color = Color.white,
                lightmapColor = Color.white
            };
            t.AddTreeInstance(instance);
            t.Flush();
        }
    }

    public void GenerateTerrain(Terrain t) {
        t.treeDistance = this.renderDistance;
        TerrainData td = t.terrainData;
        td.treeInstances = new TreeInstance[0];
        List<TreePrototype> trees = new();
        List<GameObject> models = new();
        models.AddRange(this.trees);
        models.AddRange(this.trees2);
        foreach (var tree in models) {
            TreePrototype proto = new() {
                prefab = tree
            };
            trees.Add(proto);
        }
        td.treePrototypes = trees.ToArray();
        for (int x = 0; x < td.size.x; x++) {
            for (int z = 0; z < td.size.z; z++) {
                System.Random r = new();
                double threshold = r.NextDouble();
                if (threshold > this.tree1Threshold)
                    this.GenerateTree1(r, x, z, t);
                else if (threshold < this.tree2Threshold)
                    this.GenerateTree2(r, x, z, t);
            }
        }
    }
}