using UnityEngine;

public abstract class SplatMap : MonoBehaviour {
    public abstract float[,,] GenerateSplatMap(TerrainData td);
}