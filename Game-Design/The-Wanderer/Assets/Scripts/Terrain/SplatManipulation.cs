using System.Linq;
using UnityEngine;

public class SplatManipulation : MonoBehaviour {
    public float snowSteepnessThreshold;

    public void ManipulateSplat(Enums.BiomeType type, TerrainData td, BiomeNoiseData noise, int x, int z, float[,,] splatmapData) {
        switch (type) {
            case Enums.BiomeType.MOUNTAIN:
                this.ManipulateMountainSplat(td, noise, x, z, splatmapData);
                break;
            default:
                float[] splatWeights = new float[td.alphamapLayers];
                splatWeights[noise.terrainIndexOffset] = 1.0f;
                float zz = splatWeights.Sum();
                for(int k = 0; k < td.alphamapLayers; k++){
                    splatWeights[k] /= zz;
                    splatmapData[z, x, k] = splatWeights[k];
                }
                break;
        }
    }

    public void ManipulateMountainSplat(TerrainData td, BiomeNoiseData noise, int x, int z, float[,,] splatmapData) {
        float[] splatWeights = new float[td.alphamapLayers];

        splatWeights[noise.terrainIndexOffset] = 0.5f;
        splatWeights[noise.terrainIndexOffset + 1] = 0.8f;
        float zz = splatWeights.Sum();
        for(int k = 0; k < td.alphamapLayers; k++){
            splatWeights[k] /= zz;
            splatmapData[z, x, k] = splatWeights[k];
        }
    }
}