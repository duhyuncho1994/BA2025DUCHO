# Project: An experimental comparison of active learning algorithms on fixed data sets

This project investigates how active learning algorithms perform in a passive learning environment (when only a static fixed sample (passive dataset) is available). In this setting, the learner issues membership and equivalence queries, which are answered by a passive teacher using pre-defined strategies. Various strategies are compared to handle unknown words during membership queries (MQs).

---


## Overview

- **Goal**: Use active learning algorithms (e.g., L*, TTT) on static datasets like Abbadingo
- **Setting**: Teacher answers based on the sample(Abbadingo) only, with fallback strategies for unknown queries
- **Evaluation**: Measure accuracy, MQ/EQ counts, runtime under various strategies

---

## Implemented Features

- **Active Learners** : Learning Algorithms
  - Classic L* (Angluin, 1987)
  - TTT Algorithm

- **Dataset Loading**
   - Abbadingo ```.gz``` format parser
   - Randomly generated DFAs
 
- **Equivalence Oracle**
   - Example-based teacher using dataset
      - Strategy: always yes / always no / nearest neighbor


## What Is Implemented & Where?

| File/Class                 | Description                                                  |
|----------------------------|--------------------------------------------------------------|
| `Main.java`                | Runs experiments with different strategies.                  |
| `ExampleBasedTeacher.java` | Custom teacher that simulates fixed dataset.               |
| `DatasetLoader.java`       | Loads Abbadingo-style `.gz` datasets.                        |
| `RandomDatasetGenerator.java` | Generates random data.                                    |
| `Evaluator.java`           | Calculates MQcount, EQcount, Acceptance rate, etc.
| `Algorithms.java`          | Factory for L* and TTT algorithms(Active Learners).          |
| `data/train.1.gz`          | Training sample (from Abbadingo)                             |
| `data/test.1.gz`           | Test sample (from Abbadingo)                                 |


---

## How to Compile

1. **Install Prerequisites**:
   - Java 17+
   - [Maven](https://maven.apache.org/)
   - Learnlib
   

2. **Build the project**:
```bash
mvn clean install

```
---

## How to run Experiments

### 1. Build this project
```bash
mvn clean install

```

### 2. Copy Dependency Library
```bash
mvn dependency:copy-dependencies
```

### 3. Run Main Class
#### On Unix/Linux/macOS:
```bash
java -cp "target/learnlib-demo-1.0-SNAPSHOT.jar:target/dependency/*" com.example.Main
```

#### On Windows
```bash
java -cp "target/learnlib-demo-1.0-SNAPSHOT.jar;target/dependency/*" com.example.Main
```

Or directly from your IDE (e.g., IntelliJ or VSCode).

Ensure you’ve placed the correct datasets in the ```/data/``` folder:

- **train1.gz**
- **test1.gz**

---
## How to evaluate Results

The following factors are compared depending on the strategy (e.g., Always No, Nearest Neighbor) used:

- **Dataset used (e.g., train1.gz with test1.gz)**
- **Algorithm used (e.g., L star, TTT)**
- **MQ (Membership Query) count**
- **EQ (Equivalence Query) count (rounds)**
- **Accepted count**
- **Rejected count**
- **Acceptance rate**
- **Runtime(ms)**
---
### Evaluation Criteria

| Metric | Description |
| ----------- | ----------- |
| MQcount | Total number of membership queries |
| EQcount | Counterexamples found, the number of Rounds |
| Accepted | How many samples accepted from total |
| Rejected | How many samples rejected from total |
| Acceptance Rate | % accepted samples on total samples |
| Runtime | Time taken to learn (ms) |
---
## Experiment Strategies

Membership Queries (MQs) answered using different strategies when the word is not in the sample:

-  **Always Yes** : Always return true for words that are not in the sample
-  **Always No** : Always return false for words not in the sample
-  **Nearest Neighbor** : Use the label of the most similar word among the samples
---
## Evaluation Output

| Dataset | Algorithm | Strategy       | MQs     | EQs | Symbols | accepted  | rejected | Acceptance Rate | Runtime(ms) |
|-----------|-----------|----------------|---------|-----|----------|----------|---------------| ---------------|  ---------------|
|Train1.gz  | L*        | Always No      | 809,260  | 66  | 0  | 0        | 1800          | 0% ||
|Train1.gz  | L*        | Always Yes     |  844,569  | 82 | 0  | 1800      |  0            | 100%    ||
|Train1.gz  | L*        | Nearest Neigh. | 194,501  | 42  | 0 | 1074      | 726          | 59,67% ||
|Train1.gz| TTT       | Always No       | 236,548  | 818  | 0 | 2        | 1798          | 0,11% ||
|Train1.gz| TTT       | Always Yes     |  239,894   | 645 | 0  |   1796      | 4              |  99,78%       ||
|Train1.gz| TTT       | Nearest Neigh. | 48,042  | 745    | 0 | 1,072          | 728          | 59,56% ||
|Train2.gz| L*        | Always No       |  3,824,394   | 147 | 72,415,138  |    0     |    1000          |   0%      |6,302,108 ms|
|Train2.gz| L*        | Always Yes     | 3,251,285    | 114 | 601,73,658  |     1800    |       0       |  100%       |1,312,275 ms|
|Train2.gz| L*        | Nearest Neigh. |  824,775 | 77   | 14,476,820 |  849       |     951      | 47,17% |1422479 ms|
|Train2.gz| TTT        | Always No       | 995,186    |1,964  | 175,97,194  | 2        |   1798          | 0,11%       |640,471 ms|
|Train2.gz| TTT       | Always Yes     | 835,958    | 2,147 | 142,41,109  |     1799    |       1       |  99,94%       |378,187 ms|
|Train2.gz| TTT       | Nearest Neigh. | 187,636    | 2,258 | 2,875,078  |   853      |   947          |   47,39%     |204,782 ms|
---

## Research Context

```“How can we apply active learning in a passive dataset setting using only incomplete samples?”```

- How inaccurate default answers impact learning (e.g., increase EQ count)
- When and why EQs grow large
- Which alternative strategies and Performance comparison(Which one performs best)

---




