# Project: An experimental comparison of active learning algorithms on fixed data sets

This project investigates how active learning algorithms perform when only a static fixed sample (passive dataset) is available. In this setting, the learner issues membership and equivalence queries, which are answered by a passive teacher using pre-defined strategies.

---

## Overview

- **Goal**: Use active learning algorithms (e.g., L*, TTT) on static datasets like Abbadingo
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
| `Algorithms.java`          | Factory for L* and TTT algorithms.                           |
| `data/train.1.gz`          | Training sample (from Abbadingo)                             |
| `data/test.1.gz`           | Test sample (from Abbadingo)                                 |
| `results.csv`              | Automatically written results of experiments.                |

---

## How to Compile

1. **Install Prerequisites**:
   - Java 17+
   - [Maven](https://maven.apache.org/)
   

2. **Build the project**:
```bash
mvn clean install

---

## Overview

