# TestGenerator Java Program

## Overview

`TestGenerator` is a Java program that generates random data batches and writes them to a plaintext file in `.WIS` format. Each batch contains a timestamp and multiple rows of tab-delimited random data. The timestamp for each new batch is a random number of minutes in the future from the previous batch's timestamp.

## Features

- Generates random longitude and latitude values.
- Generates random temperature values in either Celsius or Fahrenheit.
- Generates random wind speed values in km/h.
- Adds a random cardinal or ordinal direction.
- Includes a random integer (0 to 100).
- Allows the user to specify the number of batches to generate.
- Each new batch has a timestamp that is a random number of minutes in the future.

## Usage

### Prerequisites

- Java Development Kit (JDK) installed.
- A terminal or command prompt.

### Compilation

To compile the Java program, run the following command:

```sh
javac RandomNumberData.java
```

#### Execution

```sh
java RandomNumberData
```

You will then be asked to enter the number of batches to generate
