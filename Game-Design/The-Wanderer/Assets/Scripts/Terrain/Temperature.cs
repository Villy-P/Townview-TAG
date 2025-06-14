using UnityEngine;

public class Temperature : MonoBehaviour {
    public int minTemperatureSeed;
    public int maxTemperatureSeed;
    public float temperatureFrequency;
    public int temperatureOctaves;
    
    public FastNoiseLite noise;

    public void Awake() {
        this.noise = new(Random.Range(this.minTemperatureSeed, this.maxTemperatureSeed));
        this.noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        this.noise.SetFrequency(this.temperatureFrequency);
        this.noise.SetFractalOctaves(this.temperatureOctaves);
    }
}