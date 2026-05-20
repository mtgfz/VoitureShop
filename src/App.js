import { useState, useEffect } from "react";
import axios from "axios";

const API = "http://localhost:8080";

export default function App() {
  const [voitures, setVoitures] = useState([]);
  const [filtreMarque, setFiltreMarque] = useState("");
  const [filtreCouleur, setFiltreCouleur] = useState("");
  const [budget, setBudget] = useState("");
  const [aiResponse, setAiResponse] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => { fetchVoitures(); }, []);

  const fetchVoitures = () =>
    axios.get(`${API}/voitures`).then(r => setVoitures(r.data));

  const filtrerMarque = () =>
    axios.get(`${API}/voitures/marque/${filtreMarque}`).then(r => setVoitures(r.data));

  const filtrerCouleur = () =>
    axios.get(`${API}/voitures/couleur/${filtreCouleur}`).then(r => setVoitures(r.data));

  const recommander = async () => {
    setLoading(true);
    const r = await axios.get(`${API}/ai/recommend?budget=${budget}`);
    setAiResponse(r.data);
    setLoading(false);
  };

  return (
    <div style={{ fontFamily: "Arial", maxWidth: 1000, margin: "auto", padding: 20 }}>
      <h1>🚗 VoitureShop</h1>

      {/* FILTRES */}
      <div style={{ background: "#f0f0f0", padding: 15, borderRadius: 8, marginBottom: 20 }}>
        <h3>🔍 Filtres</h3>
        <input placeholder="Marque..." value={filtreMarque} onChange={e => setFiltreMarque(e.target.value)} />
        <button onClick={filtrerMarque}>Filtrer</button>
        &nbsp;&nbsp;
        <input placeholder="Couleur..." value={filtreCouleur} onChange={e => setFiltreCouleur(e.target.value)} />
        <button onClick={filtrerCouleur}>Filtrer</button>
        &nbsp;&nbsp;
        <button onClick={fetchVoitures}>Tout afficher</button>
      </div>

      {/* AI RECOMMANDATION */}
      <div style={{ background: "#e8f4fd", padding: 15, borderRadius: 8, marginBottom: 20 }}>
        <h3>🤖 IA Recommandation</h3>
        <input placeholder="Budget en MAD..." value={budget} onChange={e => setBudget(e.target.value)} />
        <button onClick={recommander} disabled={loading}>
          {loading ? "Chargement..." : "Recommander"}
        </button>
        {aiResponse && <p style={{ marginTop: 10, whiteSpace: "pre-wrap" }}>{aiResponse}</p>}
      </div>

      {/* LISTE VOITURES */}
      <h3>🚘 Voitures ({voitures.length})</h3>
      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead style={{ background: "#333", color: "white" }}>
          <tr><th>Marque</th><th>Modèle</th><th>Couleur</th><th>Année</th><th>Prix</th><th>Immat.</th></tr>
        </thead>
        <tbody>
          {voitures.map(v => (
            <tr key={v.id}>
              <td>{v.marque}</td><td>{v.modele}</td><td>{v.couleur}</td>
              <td>{v.annee}</td><td>{v.prix} MAD</td><td>{v.immatricule}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}