using UnityEngine;

public class HeightManipulation : MonoBehaviour {
    public AnimationCurve mountainTemperatureCurve;
    public AnimationCurve mountainPrecipitationCurve;

    public float ManipulateHeight(float height, float precipitationValue, float temperatureValue, Enums.BiomeType type) {
        switch (type) {
            case Enums.BiomeType.MOUNTAIN:
                return this.ManipulateMountainHeight(height, precipitationValue, temperatureValue);
        }
        return height;
    }

    public float ManipulateMountainHeight(float height, float precipitationValue, float temperatureValue) {
        return this.mountainPrecipitationCurve.Evaluate((10 * precipitationValue / 2) - 4) * 
               this.mountainTemperatureCurve.Evaluate(10 * temperatureValue / 2) * 4 * height;
    }
}