# RAKSHA: Resilience And Knowledge-driven System for Holistic Health Assurance

## Overview
**RAKSHA** is a prototype system designed to model and evaluate **resilience in space-ground systems** using a modular, Java-based architecture.  
It integrates **risk assessment**, **system health monitoring**, and a structured **Model-Based Systems Engineering (MBSE)** workflow to provide a foundation for dependable satellite and mission control applications.

This repository is part of an ongoing research project focused on:
- Enhancing reliability of spacecraft and mission operations
- Modeling risks due to system failures and environmental factors
- Providing a modular framework extendable to real-world mission scenarios

## Features
- **Risk Module**: Monitors and updates dynamic risk values for CPU, memory, network, and data packets  
- **Health Monitoring**: Continuously tracks system load and resource usage  
- **MBSE Compliance**: Modeled with use case, class, and workflow diagrams to align implementation with system architecture  
- **Modular Java Framework**: Easily extensible with additional resilience modules  

## Project Structure
RAKSHA/
├── docs/ # Diagrams and documentation
│ ├── use-case.png
│ ├── class-diagram.png
│ └── activity-diagram.png
├── src/main/java/com/spaceresilience/
│ ├── Main.java # Entry point
│ ├── RiskModule.java
│ └── HealthMonitoring.java
├── pom.xml # Maven configuration
└── README.md # Project documentation

## Requirements
- **Java 17+**  
- **Maven 3.6+**  
- Git (to clone repository)

## Getting Started (Clone & Run)
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/RAKSHA.git
   cd RAKSHA

2. **Build the project**

```mvn clean compile```


3. **Run the project**

```mvn exec:java -Dexec.mainClass=com.spaceresilience.Main```

## Expected Output

Risk Module Started
Health Monitoring Started
Risk updated: CPU -> X
CPU Load: 0.40
{Memory=0, Packets=0, Network=0, CPU=X}
Risk Module Stopped
Health Monitoring Stopped

## Results & Evaluation

- The prototype demonstrates:
- Real-time monitoring of system metrics
- Risk evaluation linked to resource usage
- Logging for further analysis (can be extended to CSV/DB backends)


## Evaluation Matrix

| Metric              | What We Observed in Prototype                         | Why It Matters                               |
| ------------------- | ----------------------------------------------------- | -------------------------------------------- |
| **Availability**        | Modules start/stop cleanly, run without crash         | Ensures system is operational when needed    |
| **Adaptability**        | Risk values update dynamically with CPU load          | System adjusts to changing resource usage    |
| **Observability**       | Outputs logs in real time (risk + health)             | Operators gain insight into system status    |
| **Containment**         | Each module isolated, errors don’t cascade            | Localizes failures, prevents total breakdown |
| **Extensibility**       | Modular Java classes (Risk, Health) allow new modules | Future-proof, can scale into full framework  |
| **Traceability (MBSE)** | Use case → class diagram → Java code link             | Validates that implementation matches design |


##  MBSE Principles

The system's architecture and design are guided by a robust MBSE approach, ensuring direct traceability from design artifacts to the final implementation.

### Design Artifacts

* **Use Case Diagram**: Captures and defines all user interactions with the system.
* **Class Diagram**: Specifies the structural architecture, with a direct mapping to Java classes in the source code.
* **Activity Diagram**: Models and documents the system's behavior and operational workflows.

### Traceability

System design artifacts are directly linked to the implementation, providing clear traceability and a strong foundation for verification and validation.


##  Future Work

The following improvements can be made on this project:

* **Fault Injection**: Add support for fault injection to simulate satellite anomalies and test system resilience under stress.
* **Data Export**: Implement functionality to export results to CSV and JSON formats for quantitative evaluation and analysis.
* **Resilience Modules**: Integrate additional resilience modules, such as redundancy and recovery, to improve system robustness.
* **MBSE Model Extension**: Extend the current MBSE models with sequence diagrams and statecharts for more detailed behavioral analysis.
* **Benchmarking**: Conduct comparative benchmarking against existing resilience frameworks to validate the system's performance.
