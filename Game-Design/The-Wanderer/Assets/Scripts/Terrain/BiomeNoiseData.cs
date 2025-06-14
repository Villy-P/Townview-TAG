using UnityEngine;

public class BiomeNoiseData : MonoBehaviour {
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

    public FastNoiseLite noise;

    public TerrainLayer[] terrainLayers;
    public int terrainIndexOffset;

    public void InitializeNoise(int seed) {
        FastNoiseLite noise = new(seed);
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
        this.noise = noise;
    }
}