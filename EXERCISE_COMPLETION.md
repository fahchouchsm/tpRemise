# TP Remise - Exercice Complété ✅

## 📋 Résumé de l'exercice

Ce projet implémente un système de calcul de remises avec persistance en base de données et traçage des transactions.

---

## 🎯 Objectifs réalisés

### ✅ 1. Trois implémentations de `ReductionService`

#### a) **ReductionFixService** (Taux fixe)
- **Package**: `org.example.tpremise.service`
- **Description**: Applique un taux de remise fixe de 4%
- **Localisation**: `src/main/java/org/example/tpremise/service/ReductionFixService.java`

#### b) **ReductionVarService** (Taux variable)
- **Package**: `org.example.tpremise.service`
- **Description**: Applique des taux variables selon tranches de montant
  - < 1000 € : 0% de remise
  - 1000-10000 € : 2% de remise
  - > 10000 € : 5% de remise
- **Localisation**: `src/main/java/org/example/tpremise/service/ReductionVarService.java`

#### c) **ReductionDBService** (Taux depuis BDD) ⭐ NOUVELLE
- **Package**: `org.example.tpremise.service`
- **Description**: Récupère les taux de remise depuis la table `REMISE` de la base de données
- **Localisation**: `src/main/java/org/example/tpremise/service/ReductionDBService.java`

---

### ✅ 2. Base de données H2 embarquée

**Configuration** dans `src/main/resources/application.yaml`:
```yaml
datasource:
  url: jdbc:h2:mem:testdb
  driver-class-name: org.h2.Driver
  username: sa
  password:
```

**Tables créées automatiquement**:

#### Table `REMISE`
```sql
CREATE TABLE REMISE (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    montant_min  DOUBLE NOT NULL,
    montant_max  DOUBLE NOT NULL,
    taux         DOUBLE NOT NULL
);
```

#### Table `TRANSACTION`
```sql
CREATE TABLE TRANSACTION (
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    date           TIMESTAMP NOT NULL,
    montant_avant  DOUBLE NOT NULL,
    montant_apres  DOUBLE NOT NULL,
    remise_id      BIGINT REFERENCES REMISE(id)
);
```

---

### ✅ 3. Service de Transaction

**Classe**: `TransactionService`
**Localisation**: `src/main/java/org/example/tpremise/service/TransactionService.java`

**Méthodes**:
- `save(double montantAvant, double montantApres, Remise remise)` - Crée une transaction
- `update(Long id, double montantAvant, double montantApres, Remise remise)` - Modifie une transaction
- `deleteById(Long id)` - Supprime une transaction
- `findById(Long id)` - Récupère une transaction
- `findAll()` - Liste toutes les transactions

**Validations**:
- ✅ Lève `RemiseException` si montants ≤ 0
- ✅ Lève `RemiseException` si transaction non trouvée

---

### ✅ 4. Gestion d'exceptions

**Classe**: `RemiseException`
**Localisation**: `src/main/java/org/example/tpremise/exception/RemiseException.java`

**Cas gérés**:
- ✅ Montant inférieur ou égal à 0
- ✅ Remise non trouvée pour le montant
- ✅ Transaction non trouvée
- ✅ Montants invalides lors de transactions

---

### ✅ 5. Tests Unitaires (TUA)

#### Cas nominaux ✓

**ReductionDBServiceTest** (5 tests):
- ✅ `testCalculerRemiseNominal()` - Calcul standard
- ✅ `testCalculerRemiseAvecDifferentTaux()` - Taux variables
- ✅ `testCalculerRemiseMontantNegatif()` - Montant négatif
- ✅ `testCalculerRemiseMontantZero()` - Montant zéro
- ✅ `testCalculerRemiseMontantNonTrouve()` - Remise inexistante

**TransactionServiceTest** (8 tests):
- ✅ `testSaveTransactionNominal()` - Création standard
- ✅ `testSaveMontantAvantNegative()` - Montant avant négatif
- ✅ `testSaveMontantApresNegative()` - Montant après négatif
- ✅ `testSaveMontantAvantZero()` - Montant avant zéro
- ✅ `testUpdateTransactionNominal()` - Modification standard
- ✅ `testUpdateTransactionNotFound()` - Transaction inexistante
- ✅ `testDeleteByIdNominal()` - Suppression standard
- ✅ `testDeleteByIdNotFound()` - Suppression inexistante

**Résultat**: 
```
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS ✅
```

---

### ✅ 6. Classe Main / Démonstration

#### MainConfig (CommandLineRunner) 
**Localisation**: `src/main/java/org/example/tpremise/MainConfig.java`

**Flux démonstratif**:
1. ➕ **Création** d'une remise en BDD
2. 🧮 **Calcul** de remise et création de transaction
3. ✏️ **Modification** de la transaction
4. 📖 **Lecture** des détails de la transaction
5. 🗑️ **Suppression** de la transaction
6. ✔️ **Vérification** de la suppression

**Sortie**:
```
=== DÉMONSTRATION DU SYSTÈME DE REMISE ===

1. Création/Récupération d'une remise...
   Remise trouvée/créée: ID=1, Taux=0.02, Plage: [0.0-1000.0]

2. Calcul de remise et création de transaction...
   Transaction créée: ID=1
   Montant avant: 2000.0
   Remise appliquée: 40.0
   Montant après: 1960.0

3. Modification de la transaction...
   Transaction modifiée: ID=1
   ...

=== FIN DE LA DÉMONSTRATION ===
```

#### DemoMain (Classe indépendante)
**Localisation**: `src/main/java/org/example/tpremise/DemoMain.java`

**Exécution**:
```bash
javac DemoMain.java
java DemoMain
```

---

## 📁 Structure du projet

```
tpRemise/
├── src/
│   ├── main/
│   │   ├── java/org/example/tpremise/
│   │   │   ├── DemoMain.java
│   │   │   ├── FacturationService.java
│   │   │   ├── MainConfig.java
│   │   │   ├── TpRemiseApplication.java
│   │   │   ├── exception/
│   │   │   │   └── RemiseException.java
│   │   │   ├── persistence/
│   │   │   │   ├── Remise.java
│   │   │   │   ├── Transaction.java
│   │   │   │   └── repository/
│   │   │   │       ├── RemiseRepository.java
│   │   │   │       └── TransactionRepository.java
│   │   │   └── service/
│   │   │       ├── ReductionService.java (Interface)
│   │   │       ├── ReductionFixService.java
│   │   │       ├── ReductionVarService.java
│   │   │       ├── ReductionDBService.java ⭐
│   │   │       ├── TransactionService.java ⭐
│   │   │       └── DataInitializationService.java
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/org/example/tpremise/
│           ├── TpRemiseApplicationTests.java
│           └── service/
│               ├── ReductionDBServiceTest.java ⭐
│               └── TransactionServiceTest.java ⭐
├── pom.xml
└── README.md
```

---

## 🚀 Exécution

### Compiler le projet
```bash
./mvnw clean compile
```

### Exécuter les tests
```bash
./mvnw test
```

### Exécuter l'application
```bash
./mvnw spring-boot:run
```

Cela affichera automatiquement la démonstration du flux CRUD des transactions.

---

## 🔧 Technologies utilisées

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **H2 Database** (base de données embarquée)
- **Hibernate 7.2.7**
- **Lombok** (annotations)
- **JUnit 5**
- **Mockito** (tests unitaires)
- **Maven** (build)

---

## 📊 Diagramme des classes

```
┌─────────────────────────────┐
│    ReductionService         │ (Interface)
│  - calculerRemise(double)   │
└──────────────┬──────────────┘
               │
    ┌──────────┼──────────┐
    ▼          ▼          ▼
┌────────────────────┐ ┌──────────────────┐ ┌────────────────────┐
│ ReductionFixService│ │ ReductionVarService│ │ ReductionDBService │⭐
│   (Taux: 4%)      │ │  (Taux variable)   │ │  (Taux en BDD)     │
└────────────────────┘ └──────────────────┘ └────────────────────┘

┌────────────────────┐
│     Remise         │ @Entity
├────────────────────┤
│ - id: Long         │
│ - montantMin       │
│ - montantMax       │
│ - taux: Double     │
└────────────────────┘
         │ 1
         │
         │ n
         ▼
┌────────────────────┐
│   Transaction      │ @Entity  ⭐
├────────────────────┤
│ - id: Long         │
│ - date: DateTime   │
│ - montantAvant     │
│ - montantApres     │
│ - remise: Remise   │
└────────────────────┘
```

---

## ✨ Fonctionnalités additionnelles

1. **DataInitializationService**: Initialise automatiquement les données de remise au démarrage de l'application
2. **Validation robuste**: Tous les montants sont validés avant traitement
3. **Traçabilité complète**: Chaque calcul est enregistré en base de données avec timestamp
4. **Tests complets**: Couverture des cas nominaux et des cas d'exception
5. **Architecture modulaire**: Trois implémentations indépendantes du service de réduction

---

## 🎓 Apprentissages clés

✅ Implémentation d'interfaces avec plusieurs stratégies
✅ Gestion de la persistance avec Spring Data JPA
✅ Base de données H2 embarquée
✅ Gestion des transactions
✅ Tests unitaires avec Mockito
✅ Gestion d'exceptions personnalisées
✅ Validation des données
✅ Configuration Spring Boot

---

## 📝 Notes

- Les entités utilisent **Lombok** pour réduire le boilerplate
- **H2** console est accessible à `/h2-console` si l'application est en mode web
- Les tests utilisent **Mockito** pour mocker les repositories
- La démonstration s'exécute automatiquement au démarrage de l'application

---

**Exercice complété avec succès! 🎉**

