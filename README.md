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
      - Strategy: always yes / always no / random / nearest neighbor


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
```bash
java -cp target/your-jar-name.jar com.example.Main
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
-  **Random** : Returns one of true/false randomly
-  **Nearest Neighbor** : Use the label of the most similar word among the samples
---
## Evaluation Output

| Dataset | Algorithm | Strategy       | MQs     | EQs | accepted  | rejected | Acceptance Rate |
|-----------|-----------|----------------|---------|-----|----------|---------------| ---------------|
|Train1.gz  | L*        | Always No      | 800000  | 66  | 100%        | 2143          | |
|Train1.gz  | L*        | Always Yes     | 800000  | 82  | 0%          | 2121          | |
|Train1.gz  | L*        | Nearest Neigh.     | 194501  | 42  | 1074          | 726          | 59,67% |
|Train1.gz| TTT       | Nearest Neigh. | 790000  | 776 | 100%          | 2018          | |
---

## Research Context

```“How can we apply active learning in a passive dataset setting using only incomplete samples?”```

- How inaccurate default answers impact learning (e.g., increase EQ count)
- When and why EQs grow large
- Which alternative strategies and Performance comparison(Which one performs best)

---




