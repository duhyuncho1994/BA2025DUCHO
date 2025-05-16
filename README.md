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
## Evaluation Output (With Abbadingo Dataset)

| Dataset | Algorithm | Strategy       | MQs     | EQs | Symbols | accepted  | rejected | Acceptance Rate | Runtime(ms) |
|-----------|-----------|----------------|---------|-----|----------|----------|---------------| ---------------|  ---------------|
|Train1.gz  | L*        | Always No      | 809,260  | 66  | 13,583,428  | 0        | 1800          | 0% |244,489 ms|
|Train1.gz  | L*        | Always Yes     |  844,569  | 82 | 14,346,195  | 1800      |  0            | 100%    |315,615 ms|
|Train1.gz  | L*        | Nearest Neigh. | 194,501  | 42  | 2,895,678 | 1074      | 726          | 59,67% |88,624 ms|
|Train1.gz| TTT       | Always No       | 236,548  | 818  | 3,718,302 | 2        | 1798          | 0,11% |93,873 ms|
|Train1.gz| TTT       | Always Yes     |  239,894   | 645 | 3,803,916  |   1796      | 4              |  99,78%       |118,243 ms|
|Train1.gz| TTT       | Nearest Neigh. | 48,042  | 745    |641289 | 1,072          | 728          | 59,56% |23,307 ms|
|Train2.gz| L*        | Always No       |  3,824,394   | 147 | 72,415,138  |    0     |    1000          |   0%      |6,302,108 ms|
|Train2.gz| L*        | Always Yes     | 3,251,285    | 114 | 60,173,658  |     1800    |       0       |  100%       |1,312,275 ms|
|Train2.gz| L*        | Nearest Neigh. |  824,775 | 77   | 14,476,820 |  849       |     951      | 47,17% |1,422,479 ms|
|Train2.gz| TTT        | Always No       | 995,186    |1,964  | 17,597,194  | 2        |   1798          | 0,11%       |640,471 ms|
|Train2.gz| TTT       | Always Yes     | 835,958    | 2,147 | 14,241,109  |     1799    |       1       |  99,94%       |378,187 ms|
|Train2.gz| TTT       | Nearest Neigh. | 187,636    | 2,258 | 2,875,078  |   853      |   947          |   47,39%     |204,782 ms|
---

### Membership Query
#### Lstar
- Since MQ is performed for all R, E combinations(Performing extensive queries to fill observation table), it requires relatively more MQ than TTT by updating the entire Observation table. MQ occurs as many times as the number of Rows x Cols.
#### TTT
- On the other hand, the MQ number is relatively low for TTT because only the comparisons necessary to distinguish between states are performed based on the discriminator.

### Equivalence Query
#### Lstar
- After constructing the hypothesis DFA, a counterexample is required only when the number of states increases due to the addition of a new state to R. Therefore, in the case of Lstar, in most cases, the number of EQs is less than or equal to the number of states.
#### TTT
- However, in case of TTT, especially in the case of DFA with complex structure, EQ is frequently performed for refinement whenever a discrimination failure occurs, because if a discrimination failure occurs, EQ is requested again and again to obtain a new Counterexample in order to find a discriminator between states.
#### Limitation of using Abbadingo

In particular, when using the Abbadingo dataset, the phenomenon of the number of EQ requests in TTT increasing significantly in an EQ oracle environment where there is no target DFA or where there is an incomplete target DFA is severe.

In both Lstar and TTT, Equivalence Query is a process of comparing the learned DFA with the actual target DFA and returning a counterexample. However, target DFA is open in the Abbadingo dataset.

So the EQ oracle must find counterexamples using a test dataset or heuristics(i.e. Nearest Neighbor Strategy). Since test dataset-based EQ is not complete, it is difficult to find all the actual state discriminations, and there may be a lack of counterexamples or uncertain situations.

TTT tries to discriminate states very strictly by using counterexamples, the counterexample must be splitted into an access sequence and a discriminator to distinguish between states using the discriminator. For example, a state ```q_0``` is accepted by targetDFA using the discriminator, but state ```q_1``` is not accepted by the targetDFA using the discriminator.

However, if there is no TargetDFA and the EQ oracle cannot provide a perfect counterexample repeatedly, TTT will keep requesting EQ multiple times to try to distinguish states.

L* is less strict and has relatively less impact. L* finds a discriminator while splitting the counterexample, and this discriminator simply distinguishes targetDFA from current hypothesis DFA.

It can operate less sensitively to the completeness of the EQ oracle.

Therefore, even in situations like Abbadingo where there is no Target DFA, the number of EQs is relatively small in case of Lstar.

### Runtime
#### Lstar
- Because of duplicate sequences (multiple rows with the same result), and because the Observation table is expanded by splitting the prefix and suffix, and because all row and column combinations are queried, memory usage is high, the number of MQs is relatively explosive. Therefore the runtime is generally long.
#### TTT
- TTT has the advantage of Redundancy-Free (only storing the minimum discriminators that can distinguish states), so it uses less memory, has a much lower number of MQs. Therefore faster.
- However, in return, the EQ count is bound to explode.

### Why TTT Can Trigger Many EQs on Complex DFAs

Imagine a DFA with 100+ states, where each state only differs from others by reading a long unique sequence of 50+ symbols (e.g., "abababab...a"). In such a case:

- **L\*** builds a table, gradually discovering distinctions across rows using counterexamples, requiring many membership queries (MQs) but fewer EQs.
- **TTT**, however, maintains a discrimination tree that must re-separate states every time a new distinguishing sequence (discriminator) is found.

As the number of states and the complexity of discriminators grow, **TTT performs many more EQs** because each insertion can restructure the tree and reverify the entire hypothesis.

> 📌 On a complex DFA derived from the Abbadingo dataset, TTT required 10x more EQs than L\*, despite similar overall accuracy — purely due to the cost of maintaining and rebalancing the tree.


## Evaluation Output (With Randomly generated Dataset)

### Relatively simple random dataset.
- Samples(Trainset) : 1000, Samples(Testset) : 300, minLength : 2, maxLength : 5
  
| Dataset | Algorithm | Strategy       | MQs     | EQs | Symbols | accepted  | rejected | Acceptance Rate | Runtime(ms) |
|-----------|-----------|----------------|---------|-----|----------|----------|---------------| ---------------|  ---------------|
|Random    | L*        | Always No      | 279      | 4 | 1602    |  122         |  178          | 40,67%       |145ms              |
|Random  | L*        | Always Yes     | 231  |4  | 1358  |  134       |   166       | 44,67% |118ms|
|Random  | L*        | Nearest Neigh. |   217 |4  | 1190  |  111       |   189       | 37,00%  |100 ms|
|Random| TTT       | Always No       | 31  |4  | 87  | 120        |  180        | 40,00% |95ms|
|Random| TTT       | Always Yes     | 27  | 4 | 73  | 114        |   186       | 38,00% |155ms|
|Random| TTT       | Nearest Neigh. |70   | 7 |280   |  123       |   177       | 41,00%  |104 ms|
---

- By simply setting up the dataset, there is no significant difference in the number of MQs or EQs in the simple DFA regardless of which algorithm is applied.
  
### Relatively complex random dataset. This could be similar with Abbadingo dataset.
- Samples(Trainset) : 3000, Samples(Testset) : 1500, minLength : 7, maxLength : 15
  
| Dataset | Algorithm | Strategy       | MQs     | EQs | Symbols | accepted  | rejected | Acceptance Rate | Runtime(ms) |
|-----------|-----------|----------------|---------|-----|----------|----------|---------------| ---------------|  ---------------|
|Random  | L*        | Always No      | 313,110  |68  |4,931,758   |  152       |   1340       | 10,13% |91,228ms|
|Random  | L*        | Always Yes     |  383,537  |68  |5,931,030   | 1195        |    305      | 79,67% |330,138ms|
|Random  | L*        | Nearest Neigh. | 61,194  |24  |882,025   |  522       |  978        |  34,80%|31,340ms|
|Random| TTT       | Always No       | 50,969  | 305 | 705,195  |  160       |  1340        | 10,67% |24,172ms|
|Random| TTT       | Always Yes     | 85,373  | 534 | 1,200,138  |  1194       |     306     |79,60%  |45,463ms|
|Random| TTT       | Nearest Neigh. | 21,674  |411  |260,506   | 489        | 1011         | 32,60% |9,476ms|
---

- As the dataset size increases, the TTT algorithm outperforms Lstar (in perspectives of significantly fewer MQs or fewer symbols used).
- However, such this result shows that the number of EQs has to increase significantly in return.

## Research Context

```“How can we apply active learning in a passive dataset setting using only incomplete samples?”```

- How inaccurate default answers impact learning (e.g., increase EQ count)
- When and why EQs grow large
- Which alternative strategies and Performance comparison(Which one performs best)

---




