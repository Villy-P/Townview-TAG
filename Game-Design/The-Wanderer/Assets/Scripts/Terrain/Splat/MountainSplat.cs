using System.Linq;
using UnityEngine;

public class MountainSplat : SplatMap {
    public float snowSteepnessThreshold;
    public float rockScale;

    public override float[,,] GenerateSplatMap(TerrainData td) {
        float[,,] splatmapData = new float[td.alphamapWidth, td.alphamapHeight, td.alphamapLayers];
        for (int y = 0; y < td.alphamapHeight; y++) {
            for (int x = 0; x < td.alphamapWidth; x++) {
                float y_01 = (float)y / (float)td.alphamapHeight;
                float x_01 = (float)x / (float)td.alphamapWidth;
                float height = td.GetHeight(Mathf.RoundToInt(y_01 * td.heightmapResolution), Mathf.RoundToInt(x_01 * td.heightmapResolution) );
                Vector3 normal = td.GetInterpolatedNormal(y_01,x_01);
                float steepness = td.GetSteepness(y_01, x_01);
                float[] splatWeights = new float[td.alphamapLayers];
                splatWeights[0] = steepness > this.snowSteepnessThreshold ? 1.0f : 0.0f;
                splatWeights[1] = this.rockScale;
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