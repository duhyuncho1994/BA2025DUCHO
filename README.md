# Project: An experimental comparison of active learning algorithms on fixed data sets

This project investigates how active learning algorithms perform in a passive learning environment (when only a static fixed sample (passive dataset) is available). In this setting, the learner issues membership and equivalence queries, which are answered by a passive teacher using pre-defined strategies. Various strategies are compared to handle unknown words during membership queries (MQs).

---

## Project Structure

learnlib-demo/ ├── src/main/java/com/example/ │ ├── Main.java │ ├── ExampleBasedTeacher.java │ ├── DatasetLoader.java │ ├── RandomDatasetGenerator.java │ ├── Evaluator.java │ ├── Algorithms.java ├── data/ │ ├── train.1.gz │ ├── test.1.gz ├── results.csv ├── pom.xml └── README.md


## Overview

- **Goal**: Use active learning algorithms (e.g., L*, TTT) on static datasets like Abbadingo
- **GGGG** : 
- **Evaluation**: Measure accuracy, MQ/EQ counts, runtime under various strategies

---


## What Is Implemented & Where?

| File/Class                 | Description                                                  |
|----------------------------|--------------------------------------------------------------|
| `Main.java`                | Runs experiments with different strategies.                  |
| `ExampleBasedTeacher.java` | Custom teacher that simulates passive dataset.               |
| `DatasetLoader.java`       | Loads Abbadingo-style `.gz` datasets.                        |
| `RandomDatasetGenerator.java` | Generates random data.                                    |
| `Evaluator.java`           | Calculates accuracy, logs results to CSV.                    |
| `Algorithms.java`          | Factory for L* and TTT algorithms(Active Learners).          |
| `data/train.1.gz`          | Training sample (from Abbadingo)                             |
| `data/test.1.gz`           | Test sample (from Abbadingo)                                 |
| `results.csv`              | Automatically written results of experiments.                |

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

## How to evaluate Results

The following factors are compared depending on the strategy (e.g., Always No, Nearest Neighbor) used:

- **Dataset used ( e.g., train1.gz with test1.gz)**
- **Algorithm used (e.g., L star, TTT)**
- **MQ (Membership Query) count**
- **EQ (Equivalence Query) count (rounds)**
- **Accuracy on test set**
- **Runtime(ms)**

### Evaluation Criteria

| Metric | Description |
| ----------- | ----------- |
| MQcount | Total number of membership queries |
| EQcount | Counterexamples found, the number of Rounds |
| Accuracy | % correct on test set |
| Runtime | Time taken to learn (ms) |

## Experiment Strategies

Membership Queries (MQs) answered using different strategies when the word is not in the sample:

-  Always Yes
-  Always No
-  Random
-  Nearest Neighbor ((Hamming / Edit Distance)

## Evaluation Output

| Dataset | Algorithm | Strategy       | MQs     | EQs | Accuracy  | Runtime (ms) |
|-----------|-----------|----------------|---------|-----|----------|---------------|
|Train1.gz  | L*        | Always No      | 800000  | 66  | 100%        | 2143          |
|Train1.gz  | L*        | Always Yes     | 800000  | 82  | 0%          | 2121          |
|Train1.gz| L*        | Nearest Neigh. | 790000  | 40  | 95%           | 1874          |
|Train1.gz| TTT       | Nearest Neigh. | 790000  | 776 | 100%          | 2018          |
