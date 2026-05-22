# VoitureShop — Application Full Stack avec IA

**Étudiante :** LEMTOUGUI Fatima-Ezzahrae  
**Filière :** D2S — 2ème Année  
**Établissement :** ENSIAS  
**Encadrant :** Pr. Khalid Nafil

---

## Description du projet

VoitureShop est une application Full Stack de gestion de véhicules intégrant :

- **Backend** Spring Boot (Java 17) avec API REST CRUD
- **Base de données** MySQL 8
- **Intelligence Artificielle** via Ollama (llama2) — recommandations de voitures par budget et analyse de modèles
- **Frontend** React.js avec filtres dynamiques (marque, couleur) et interface IA
- **Conteneurisation** Docker + Docker Compose
- **Orchestration** Kubernetes (Minikube) — Lab 2 complet avec ConfigMap, Secrets, PersistentVolumeClaim

---

## Prérequis — Logiciels à installer

| Logiciel | Version recommandée | Téléchargement |
|---|---|---|
| Java JDK | 17 | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org |
| Docker Desktop | Dernière version | https://www.docker.com/products/docker-desktop |
| Minikube | 1.38+ | https://minikube.sigs.k8s.io/docs/start |
| kubectl | 1.34+ | https://kubernetes.io/docs/tasks/tools |
| Node.js + npm | 18+ | https://nodejs.org |
| Git | Dernière version | https://git-scm.com |

---

## Structure du projet

```
VoitureShop/
├── src/                          # Code source Spring Boot
├── k8s/                          # Fichiers Kubernetes (Lab 2)
│   ├── mysql-configMap.yaml      # ConfigMap base de données
│   ├── mysql-secrets.yaml        # Secrets (credentials chiffrés)
│   ├── db-deployment.yaml        # Déploiement MySQL + PVC + Service
│   └── app-deployment.yaml       # Déploiement Spring Boot (3 replicas) + Service
├── Dockerfile                    # Image Docker du backend
├── docker-compose.yml            # Orchestration locale complète
└── pom.xml
```

---

## Option 1 — Lancement rapide avec Docker Compose

> Le professeur peut lancer l'application complète avec **une seule commande**.

### Étape 1 — Cloner le projet

```bash
git clone https://github.com/mtgfz/VoitureShop.git
cd VoitureShop
```

### Étape 2 — Lancer tout avec Docker Compose

```bash
docker-compose up -d --build
```

Docker va automatiquement :
- Démarrer MySQL 8
- Démarrer Ollama (IA)
- Builder et démarrer le backend Spring Boot

### Étape 3 — Vérifier que tout tourne

```bash
docker ps
```

### Étape 4 — Tester l'API

Ouvrir dans le navigateur :
- http://localhost:8080/voitures
- http://localhost:8080/voitures/marque/Toyota
- http://localhost:8080/ai/recommend?budget=100000

### Étape 5 — Lancer le Frontend React

```bash
cd ../voiture-frontend   # ou cloner séparément depuis la branche frontend
npm install
npm start
```

Frontend disponible sur : http://localhost:3000

---

## Option 2 — Déploiement Kubernetes (Lab 2)

### Étape 1 — Prérequis Kubernetes

```bash
minikube start --driver=docker
```

### Étape 2 — Charger l'image dans Minikube

```bash
# Builder le jar d'abord
mvn clean package -DskipTests

# Builder l'image Docker
docker build -t voitureshop-backend:latest .

# Charger dans Minikube
minikube image load voitureshop-backend:latest
```

### Étape 3 — Appliquer ConfigMap et Secrets

```bash
kubectl apply -f k8s/mysql-configMap.yaml
kubectl get configmap

kubectl apply -f k8s/mysql-secrets.yaml
kubectl get secrets
```

### Étape 4 — Déployer MySQL

```bash
kubectl apply -f k8s/db-deployment.yaml
kubectl get deployments
kubectl get pods
```

### Étape 5 — Déployer Spring Boot (3 replicas)

```bash
kubectl apply -f k8s/app-deployment.yaml
kubectl get deployments
kubectl get pods
kubectl get svc
```

### Étape 6 — Accéder au service

```bash
minikube service springboot-crud-svc --url
```

Utiliser l'URL générée pour tester l'API (ex: http://127.0.0.1:XXXXX/voitures)

### Étape 7 — Dashboard Kubernetes (optionnel)

```bash
minikube dashboard
```

### Arrêt propre

```bash
minikube stop
minikube delete
```

---

## Fonctionnalités

| Endpoint | Méthode | Description |
|---|---|---|
| `/voitures` | GET | Liste toutes les voitures |
| `/voitures/{id}` | GET | Détail d'une voiture |
| `/voitures` | POST | Ajouter une voiture |
| `/voitures/{id}` | PUT | Modifier une voiture |
| `/voitures/{id}` | DELETE | Supprimer une voiture |
| `/voitures/marque/{marque}` | GET | Filtrer par marque |
| `/voitures/couleur/{couleur}` | GET | Filtrer par couleur |
| `/ai/recommend?budget=X` | GET | Recommandation IA par budget |
| `/ai/analyze?marque=X&modele=Y` | GET | Analyse IA d'un modèle |

---

## Architecture Kubernetes (Lab 2)

```
Minikube Node
├── Pod: mysql (1 replica)
│   ├── Container: mysql:8
│   ├── PersistentVolumeClaim: mysql-pv-claim (1Gi)
│   └── Service: mysql (ClusterIP: None — DNS)
│
└── Pod: springboot-crud-deployment (3 replicas)
    ├── Container: voitureshop-backend:latest
    ├── ConfigMap: db-config (host, dbName)
    ├── Secret: mysql-secrets (username, password)
    └── Service: springboot-crud-svc (NodePort: 8080)
```

---

## Technologies utilisées

- Java 17 — Spring Boot 3
- Spring Data JPA — Hibernate
- Spring AI — Ollama (llama2)
- MySQL 8
- React.js — Axios
- Docker — Docker Compose
- Kubernetes — Minikube
- Maven
