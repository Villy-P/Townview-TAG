using System.Linq;
using UnityEngine;

public class DesertSplat : SplatMap {
    public float pathHeightThreshold;
    public float sandScale;

    public FastNoiseLite.NoiseType pathNoiseType;
    public FastNoiseLite.FractalType pathFractalType;
    public float pathFrequency;
    public int pathOctaves;

    public override float[,,] GenerateSplatMap(TerrainData td) {
        System.Random r = new();

        FastNoiseLite noise = new(r.Next(0, 1000000));
        noise.SetNoiseType(this.pathNoiseType);
        noise.SetFractalType(this.pathFractalType);
        noise.SetFrequency(this.pathFrequency);
        noise.SetFractalOctaves(this.pathOctaves);

        float[,,] splatmapData = new float[td.alphamapWidth, td.alphamapHeight, td.alphamapLayers];
        for (int y = 0; y < td.alphamapHeight; y++) {
            for (int x = 0; x < td.alphamapWidth; x++) {
                float y_01 = (float)y / (float)td.alphamapHeight;
                float x_01 = (float)x / (float)td.alphamapWidth;
                float[] splatWeights = new float[td.alphamapLayers];
                splatWeights[0] = this.sandScale;
                splatWeights[1] = noise.GetNoise(y, x) > this.pathHeightThreshold ? 1.0f : 0.0f;
                float z = splatWeights.Sum();
                for(int i = 0; i< td.alphamapLayers; i++){
                    splatWeights[i] /= z;
                    splatmapData[x, y, i] = splatWeights[i];
                }
            }
        }
        return splatmapData;
    }
}