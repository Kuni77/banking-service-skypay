# Banking Service - Test Technique Skypay

Solution pour le test technique de développeur Java chez Skypay.

## Description

Implémentation d'un système bancaire simple permettant de :
- Déposer de l'argent
- Retirer de l'argent
- Afficher un relevé de compte (ordre chronologique inverse)

## Démarrage rapide

### Prérequis
- Java 11 ou supérieur
- Maven 3.6+ ou Gradle 7+
- JUnit 5 (pour les tests)

### Installation

```bash
# Cloner le repository
git clone https://github.com/kuni77/banking-service-skypay.git
cd banking-service

# Compiler le projet
mvn clean compile

# Lancer les tests
mvn test

# Exécuter la démo
mvn exec:java -Dexec.mainClass="Main"
```

## Structure du projet

```
src/
├── main/java/
│   ├── AccountService.java      # Interface imposée
│   ├── Transaction.java         # Modèle de transaction
│   ├── Account.java             # Implémentation principale
│   └── Main.java                # Démonstration
└── test/java/
    └── AccountTest.java         # Tests unitaires (12 tests)
```

## Fonctionnalités

### Scénario d'acceptation
```java
Account account = new Account();
account.deposit(1000, LocalDate.of(2012, 1, 10));
account.deposit(2000, LocalDate.of(2012, 1, 13));
account.withdraw(500, LocalDate.of(2012, 1, 14));
account.printStatement();
```

**Output:**
```
Date       || Amount || Balance
14/01/2012 || -500   || 2500
13/01/2012 || 2000   || 3000
10/01/2012 || 1000   || 1000
```

### Gestion des exceptions

**Montants invalides**
```java
account.deposit(-100);  // ❌ Exception: amount must be positive
account.withdraw(0);     // ❌ Exception: amount must be positive
```

**Solde insuffisant**
```java
account.deposit(500);
account.withdraw(600);   // ❌ Exception: Insufficient funds
```

**Dates invalides**
```java
account.deposit(100, null);                      // ❌ Exception: date cannot be null
account.deposit(100, LocalDate.now().plusDays(1)); // ❌ Exception: cannot be in the future
```

## Tests

12 tests unitaires couvrant :
- Scénario d'acceptation complet
- Opérations valides (dépôts, retraits)
- Gestion des exceptions (montants, solde, dates)
- Transactions multiples en séquence
- Test de performance (10 000 transactions)

```bash
# Lancer tous les tests
mvn test

# Lancer un test spécifique
mvn test -Dtest=AccountTest#testAcceptanceScenario
```

### Résultats des tests
```
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] Performance: 10000 transactions in ~150ms
```

## Choix de conception

### Simplicité
- Respect de la consigne "simplest solution"
- Code lisible et maintenable
- Pas de sur-ingénierie

### Performance
- `ArrayList` pour stockage en mémoire (O(1) pour ajout)
- Pas de copie inutile des données
- Efficace même avec des milliers de transactions

### Validation robuste
- Tous les paramètres sont validés
- Messages d'erreur explicites
- Prévention des états incohérents

### Thread-safety
**Note:** Cette implémentation ne gère pas les accès concurrents car l'exercice demande explicitement "the simplest solution" et utilise des `ArrayList` en mémoire sans persistance.

Pour une application production, j'ajouterais :
- Synchronisation avec `synchronized` ou `ReentrantLock`
- Transactions atomiques
- Tests de concurrence

## Dépendances Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Exemple d'utilisation

```java
public class Example {
    public static void main(String[] args) {
        Account account = new Account();
        
        // Dépôt
        account.deposit(1000);
        System.out.println("Balance: " + account.getBalance()); // 1000
        
        // Retrait
        account.withdraw(300);
        System.out.println("Balance: " + account.getBalance()); // 700
        
        // Relevé
        account.printStatement();
    }
}
```

## Conformité aux exigences

- Interface `AccountService` respectée
- Méthodes `deposit()`, `withdraw()`, `printStatement()`
- Utilisation d'`ArrayList` (pas de repository)
- Gestion complète des exceptions
- Tests unitaires exhaustifs
- Performance optimisée
- Format de sortie conforme (dd/MM/yyyy)
- Ordre chronologique inverse

## Auteur

**Amadou Diao**  
Test technique pour Skypay - Décembre 2025

## 📄 Licence

Ce projet est réalisé dans le cadre d'un test technique.
