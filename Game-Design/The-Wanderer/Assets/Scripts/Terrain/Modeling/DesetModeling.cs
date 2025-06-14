using System;
using System.Collections.Generic;
using UnityEngine;

public class DesertModeling : Modeling {
    public GameObject[] cactiModel;
    public double cactiThreshold;
    public int cactiMin;
    public int cactiMax;
    public int cactiOffsetMin;
    public int cactiOffsetMax;

    public GameObject[] rockModel;
    public double rockThreshold;
    public int rockMin;
    public int rockMax;
    public int rockOffsetMin;
    public int rockOffsetMax;

    public int renderDistance;

    public void GenerateCacti(System.Random r, int x, int z, Terrain t) {
        int cactiCount = r.Next(this.cactiMin, this.cactiMax);
        for (int i = 0; i < cactiCount; i++) {
            float xOff = (float)(this.cactiOffsetMin + (this.cactiOffsetMax - this.cactiOffsetMin) * r.NextDouble());
            float zOff = (float)(this.cactiOffsetMin + (this.cactiOffsetMax - this.cactiOffsetMin) * r.NextDouble());

            Vector3 worldPos = new((x + xOff) / t.terrainData.size.x, 0, (z + zOff) / t.terrainData.size.z);
            int modelIndex = r.Next(0, this.cactiModel.Length - 1);
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

    public void GenerateRock(System.Random r, int x, int z, Terrain t) {
        int rockCount = r.Next(this.rockMin, this.rockMax);
        for (int i = 0; i < rockCount; i++) {
            float xOff = (float)(this.rockOffsetMin + (this.rockOffsetMax - this.rockOffsetMin) * r.NextDouble());
            float zOff = (float)(this.rockOffsetMin + (this.rockOffsetMax - this.rockOffsetMin) * r.NextDouble());

            Vector3 worldPos = new((x + xOff) / t.terrainData.size.x, 0, (z + zOff) / t.terrainData.size.z);
            worldPos.y = t.SampleHeight(worldPos);
            int modelIndex = r.Next(0, this.rockModel.Length - 1) + this.cactiModel.Length;
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

    public override void GenerateTerrain(Terrain t) {
        t.treeDistance = this.renderDistance;
        TerrainData td = t.terrainData;
        td.treeInstances = new TreeInstance[0];
        List<TreePrototype> trees = new();
        List<GameObject> models = new();
        models.AddRange(this.cactiModel);
        models.AddRange(this.rockModel);
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
                if (threshold > this.cactiThreshold)
                    this.GenerateCacti(r, x, z, t);
                else if (threshold < this.rockThreshold)
                    this.GenerateRock(r, x, z, t);
            }
        }
    }
}