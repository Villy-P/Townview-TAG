using UnityEngine;

namespace Enums {
    public enum BiomeType {
        DESERT,
        FOREST,
        MOUNTAIN,
        CANYON,
    };
}

public class BiomeData : MonoBehaviour {
    public BiomeNoiseData[] noiseData;
    public TreeGeneration[] treeGeneration;

    public static Enums.BiomeType GetBiomeAtLocation(float temperature, float precipitation) {
        if (temperature > 0.9 && precipitation < 0.1)
            return Enums.BiomeType.DESERT;
        if (temperature < 0.2 && precipitation > 0.8)
            return Enums.BiomeType.MOUNTAIN;
        return Enums.BiomeType.FOREST;
    }
}