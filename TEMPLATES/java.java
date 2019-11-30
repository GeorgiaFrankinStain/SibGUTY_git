//файл чтение запись серилизация аррайЛиста
    public void writeBigIntegersToFile(ArrayList<BigInteger> materialArrListBI, File outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeInt(materialArrListBI.size());

            for(BigInteger BigInteger: materialArrListBI) {
                objectOutputStream.writeObject(BigInteger);
            }
        }
    }

    public void readBigIntegersFromFile(ArrayList<BigInteger> materialArrListBI, File inputFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(inputFile);

        try(ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            int BigIntegerCount = objectInputStream.readInt();

            for (int i = 0; i < BigIntegerCount; i++) {
                materialArrListBI.add((BigInteger)objectInputStream.readObject());
            }
        }
    }