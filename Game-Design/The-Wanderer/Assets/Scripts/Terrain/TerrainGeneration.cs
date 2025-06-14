using System;
using System.Collections.Generic;
using System.Linq;
using Unity.VisualScripting;
using UnityEngine;

public class TerrainGeneration : MonoBehaviour {
    public int minHeightSeed;
    public int maxHeightSeed;

    public int terrainGridWidth;
    public int terrainGridHeight;

    public int terrainWidth;
    public int terrainHeight;
    public int terrainLength;

    public BiomeData biomeData;

    public int mapDepth;
    public int mapWidth;

    public int scale;
    public float positionScale;

    public Precipitation precipitation;
    public Temperature temperature;

    public TerrainLayer[] terrainLayers;
    public HeightManipulation height;
    public SplatManipulation splat;

    public void Start() {
        this.InitializeNoise();
        for (int i = 0; i < this.terrainGridWidth; i++) {
            for (int j = 0; j < this.terrainGridHeight; j++) {
                
                string name = "Terrain" + i + "" + j;
                Vector3 position = new(i * this.terrainWidth * 2, 0, j * this.terrainHeight * 2);
                Vector3 size = new(this.terrainWidth / 16.0f, this.terrainLength, this.terrainHeight / 16.0f);

                TerrainData terrainData = new() {
                    size = size,
                    name = name,
                    heightmapResolution = this.mapDepth,
                    alphamapResolution = this.mapDepth
                };

                List<TerrainLayer> list = new();
                foreach (var data in this.biomeData.noiseData) {
                    data.terrainIndexOffset = list.Count;
                    list.AddRange(data.terrainLayers);
                }
                terrainData.terrainLayers = list.ToArray();

                float[,] noiseMap = new float[this.mapDepth, this.mapWidth];
                float[,,] splatmapData = new float[this.mapDepth, this.mapWidth, terrainData.alphamapLayers];
                for (int zIndex = 0; zIndex < this.mapDepth; zIndex++) {
                    for (int xIndex = 0; xIndex < this.mapWidth; xIndex++) {
                        float sampleX = (xIndex + (this.mapDepth * i)) / this.positionScale;
                        float sampleZ = (zIndex + (this.mapDepth * j)) / this.positionScale;
                        float temperatureValue = this.Normalize(this.temperature.noise.GetNoise(sampleX, sampleZ));
                        float precipitationValue = this.Normalize(this.precipitation.noise.GetNoise(sampleX, sampleZ));
                        int biomeDataIndex = (int)BiomeData.GetBiomeAtLocation(temperatureValue, precipitationValue);

                        BiomeNoiseData noise = this.biomeData.noiseData[biomeDataIndex];
                        float n = noise.noise.GetNoise(sampleX, sampleZ) + noise.add;
                        n = this.height.ManipulateHeight(n, precipitationValue, temperatureValue, (Enums.BiomeType)biomeDataIndex);
                        noiseMap[zIndex, xIndex] = n / noise.scale;
                    }
                }

                terrainData.SetHeights(0, 0, noiseMap);

                for (int y = 0; y < this.mapDepth; y++) {
                    for (int x = 0; x < this.mapWidth; x++) {
                        float sampleX = (x + (this.mapDepth * i)) / this.positionScale;
                        float sampleZ = (y + (this.mapDepth * j)) / this.positionScale;
                        float temperatureValue = this.Normalize(this.temperature.noise.GetNoise(sampleX, sampleZ));
                        float precipitationValue = this.Normalize(this.precipitation.noise.GetNoise(sampleX, sampleZ));
                        int biomeDataIndex = (int)BiomeData.GetBiomeAtLocation(temperatureValue, precipitationValue);
                        BiomeNoiseData noise = this.biomeData.noiseData[biomeDataIndex];

                        this.splat.ManipulateSplat((Enums.BiomeType)biomeDataIndex, terrainData, noise, x, y, splatmapData);
                    }
                }

                terrainData.SetAlphamaps(0, 0, splatmapData);

                GameObject terrain = Terrain.CreateTerrainGameObject(terrainData);
                terrain.transform.position = position;
                terrain.name = name;

                this.biomeData.treeGeneration[0].GenerateTerrain(terrain.GetComponent<Terrain>());

                Debug.Log(terrain.GetComponent<Terrain>().terrainData.treeInstanceCount);
                terrain.GetComponent<Terrain>().treeBillboardDistance = 100000;
            }
        }
    }

    public float Normalize(float value) {
        return (value + 1) / 2f;
    }

    public void InitializeNoise() {
        int seed = UnityEngine.Random.Range(this.minHeightSeed, this.maxHeightSeed);
        foreach (var item in this.biomeData.noiseData)
            item.InitializeNoise(seed);
    }

    public void Update() {
        if (Input.GetKeyDown(KeyCode.R))
            Start();
    }
}