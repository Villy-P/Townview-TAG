using System;
using UnityEngine;

public class PerlinNoise : MonoBehaviour {
    public FastNoiseLite.NoiseType noiseType;
    public FastNoiseLite.FractalType fractalType;
    public float frequency;
    public int octaves;
    public float scale;
    public float add = 1;

    public FastNoiseLite.CellularDistanceFunction cellularDistanceFunction;
    public FastNoiseLite.CellularReturnType cellularReturnType;
    public int cellularJitter;

    public FastNoiseLite.DomainWarpType domainWarpType;
    public int domainWarpAmp;

    public float fractalGain;
    public float fractalLacunarity;

    public bool cliff = false;

    public float[,] GenerateNoiseMap(int mapDepth, int mapWidth, float scale) {
        System.Random r = new();

        float W = (float)r.NextDouble();

        FastNoiseLite noise = new(r.Next(0, 1000000));
        noise.SetNoiseType(this.noiseType);
        noise.SetFractalType(this.fractalType);
        noise.SetFrequency(this.frequency);
        noise.SetFractalOctaves(this.octaves);
        if (this.noiseType == FastNoiseLite.NoiseType.Cellular) {
            noise.SetCellularDistanceFunction(this.cellularDistanceFunction);
            noise.SetCellularReturnType(this.cellularReturnType);
            noise.SetCellularJitter(this.cellularJitter);
        }
        noise.SetDomainWarpAmp(this.domainWarpAmp);
        noise.SetDomainWarpType(this.domainWarpType);
        noise.SetFractalGain(this.fractalGain);
        noise.SetFractalLacunarity(this.fractalLacunarity);
        
        float[,] noiseMap = new float[mapDepth, mapWidth];
        for (int zIndex = 0; zIndex < mapDepth; zIndex ++) {
            for (int xIndex = 0; xIndex < mapWidth; xIndex++) {
                float sampleX = xIndex / this.scale;
                float sampleZ = zIndex / this.scale;
                float n = noise.GetNoise(sampleX, sampleZ) + this.add;
                if (this.cliff) {
                    // n = Mathf.Round(n - 1.0f) + 0.5f * Mathf.Pow(2 * (n - Mathf.Round(n)), 11);
                    float K = Mathf.Floor(n / W);
                    float F = (n - K * W) / W;
                    float S = Mathf.Min(2 * F, 1.0f);
                    n = (K + S) * W;
                }
                noiseMap[zIndex, xIndex] = n / scale;
            }
        }
        return noiseMap;
    }
}