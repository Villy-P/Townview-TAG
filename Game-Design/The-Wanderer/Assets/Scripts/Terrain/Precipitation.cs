using UnityEngine;

public class Precipitation : MonoBehaviour {
    public int minPercipitationSeed;
    public int maxPercipitationSeed;
    public float precipitationFrequency;
    public int precipitationOctaves;

    public FastNoiseLite noise;

    public void Awake() {
        this.noise = new(Random.Range(this.minPercipitationSeed, this.maxPercipitationSeed));
        this.noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        this.noise.SetFrequency(this.precipitationFrequency);
        this.noise.SetFractalOctaves(this.precipitationOctaves);
    }
}