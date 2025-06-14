using System.Collections.Generic;
using UnityEngine;

public class ForestModeling : Modeling {
    public GameObject[] treeModels;
    public double treeThreshold;
    
    public FastNoiseLite.NoiseType noiseType;
    public float frequency;

    public float renderDistance;

    public override void GenerateTerrain(Terrain t) {
        TerrainData td = t.terrainData;
        t.treeDistance = this.renderDistance;
        td.treeInstances = new TreeInstance[0];
        List<TreePrototype> trees = new();
        foreach (var tree in this.treeModels) {
            TreePrototype proto = new() {
                prefab = tree
            };
            trees.Add(proto);
        }
        td.treePrototypes = trees.ToArray();
        for (int x = 0; x < td.size.x; x++) {
            for (int z = 0; z < td.size.z; z++) {
                System.Random r = new();
                FastNoiseLite noise = new(r.Next(0, 10000));
                noise.SetNoiseType(this.noiseType);
                noise.SetFrequency(this.frequency);
                
                double n = noise.GetNoise(x, z) * 100;
                if (n > Random.Range(1f, 10000f)) {
                    Vector3 worldPos = new(x / t.terrainData.size.x, 0, z / t.terrainData.size.z);
                    int modelIndex = r.Next(0, this.treeModels.Length - 1);
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
        }
        Debug.Log(td.treeInstanceCount);
    }
}