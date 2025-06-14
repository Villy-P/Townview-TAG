using UnityEngine;

public class TileGeneration : MonoBehaviour {
    [SerializeField]
    private PerlinNoise noiseMapGeneration;
    [SerializeField]
    private float mapScale;
    [SerializeField]
    private SplatMap splatGen;
    [SerializeField]
    private Modeling modeling;

    public bool change = false;

    void Start() {
        GenerateTile();
    }

    void GenerateTile() {
        Terrain t = GetComponent<Terrain>();
        TerrainData td = t.terrainData;
        float[,] heightMap = this.noiseMapGeneration.GenerateNoiseMap(513, 513, this.mapScale);
        td.SetHeights(0, 0, heightMap);
        if (this.splatGen != null) {
            float [,,] splatMap = this.splatGen.GenerateSplatMap(td);
            td.SetAlphamaps(0, 0, splatMap);
        }
        if (this.modeling != null)
            modeling.GenerateTerrain(t);
    }

    void Update() {
        if (Input.GetKey("down") && this.change)
            this.Start();
    }
}