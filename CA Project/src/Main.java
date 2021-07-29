import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;


public class Main {
    static int[] memory = new int[2048];
    static int[] registers = new int[32];
    static Hashtable<Integer, String> trace = new Hashtable<>();
    static Integer PC = 0;
    static int instructionCount = 0;

    static int cycles = 1;

    public static void main(String[] args) {

        readFile("Test2");
        int temp = 4;
        while (PC < instructionCount || temp > 0) {
            if (PC >= instructionCount) {
                temp--;
            }
            System.out.println("Clock Cycle: " + cycles);
            WriteBack.writeBack();
            Memory.memory();
            Execute.execute();
            Decode.decode();
            Fetch.fetch();
            cycles++;
            System.out.println("__________________________________________________________");
        }
        printMemoryContent();

        printRegisterFileContent();
    }

    public static int regValue(String r) {
        switch (r) {
            case "R0":
                return 0;
            case "R1":
                return 1;
            case "R2":
                return 2;
            case "R3":
                return 3;
            case "R4":
                return 4;
            case "R5":
                return 5;
            case "R6":
                return 6;
            case "R7":
                return 7;
            case "R8":
                return 8;
            case "R9":
                return 9;
            case "R10":
                return 10;
            case "R11":
                return 11;
            case "R12":
                return 12;
            case "R13":
                return 13;
            case "R14":
                return 14;
            case "R15":
                return 15;
            case "R16":
                return 16;
            case "R17":
                return 17;
            case "R18":
                return 18;
            case "R19":
                return 19;
            case "R20":
                return 20;
            case "R21":
                return 21;
            case "R22":
                return 22;
            case "R23":
                return 23;
            case "R24":
                return 24;
            case "R25":
                return 25;
            case "R26":
                return 26;
            case "R27":
                return 27;
            case "R28":
                return 28;
            case "R29":
                return 29;
            case "R30":
                return 30;
            case "R31":
                return 31;
        }
        return 0;
    }

    public static void printMemoryContent() {

        System.out.println("Printing Memory Content:");
        System.out.println("Instructions:");
        StringBuilder sb = new StringBuilder("#");
        for (int i = 0; i < 1024; i++) {
            sb.append(i);
            sb.append(":\t");
            sb.append(intToBinary(memory[i]));
            sb.append("\t(Decimal: ");
            sb.append(memory[i]);
            sb.append("), Assembly:");
            sb.append(trace.get(memory[i]));
            System.out.println(sb);
            sb.setLength(1);
        }
        System.out.println("Data:");
        for (int i = 1024; i < 2048; i++) {
            sb.append(i);
            sb.append(":\t");
            sb.append(intToBinary(memory[i]));
            sb.append("\t(Decimal: ");
            sb.append(memory[i]);
            sb.append(")");
            System.out.println(sb);
            sb.setLength(1);
        }
        System.out.println("________________________________");
    }

    public static void printRegisterFileContent() {
        System.out.println("Printing Register File Content:");
        StringBuilder sb = new StringBuilder("R");
        for (int i = 0; i <= 31; i++) {
            sb.append(i);
            sb.append(":\t");
            sb.append(intToBinary(registers[i]));
            sb.append("\t(Decimal: ");
            sb.append(registers[i]);
            sb.append(")");
            System.out.println(sb);
            sb.setLength(1);
        }
        System.out.println("________________________________");

    }

    private static String intToBinary(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(((1) & number));
            number >>= 1;
        }
        sb.reverse();
        return sb.toString();
    }

    public static void readFile(String fileName) {
        trace.put(0,"ADD R0 R0 R0");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
            int i = 0;
            while (br.ready()) {
                int inserted = 0;
                String s = br.readLine();
                StringTokenizer st = new StringTokenizer(s, " ");
                switch (st.nextToken()) {
                    case "ADD":
                        inserted |= 0 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "SUB":
                        inserted |= 1 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "MUL":
                        inserted |= 2 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "MOVI":
                        inserted |= 3 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= (Integer.parseInt(st.nextToken()) & 0b00000000000000111111111111111111);
                        break;
                    case "JEQ":
                        inserted |= 4 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= (Integer.parseInt(st.nextToken()) & 0b00000000000000111111111111111111);
                        break;
                    case "AND":
                        inserted |= 5 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "XORI":
                        inserted |= 6 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= (Integer.parseInt(st.nextToken()) & 0b00000000000000111111111111111111);
                        break;
                    case "JMP":
                        inserted |= 7 << 28;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "LSL":
                        inserted |= 8 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken())& 0b00000000000000000001111111111111;
                        break;
                    case "LSR":
                        inserted |= 9 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken()) & 0b00000000000000000001111111111111;
                        break;
                    case "MOVR":
                        inserted |= 10 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= (Integer.parseInt(st.nextToken()) & 0b00000000000000111111111111111111);
                        break;
                    case "MOVM":
                        inserted = inserted | 11 << 28;
                        System.out.println(inserted);
                        inserted = inserted | regValue(st.nextToken()) << 23;
                        inserted = inserted | regValue(st.nextToken()) << 18;
                        inserted |= (Integer.parseInt(st.nextToken()) & 0b00000000000000111111111111111111);
                        break;
                }
                trace.put(inserted, s);
                memory[i] = inserted;
                i++;
            }
            instructionCount = i;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Fetch {
        //input: none
        //output:
        static Integer instruction;

        static boolean pause = false;

        public static void fetch() {
            if (cycles % 2 == 1) {
                System.out.print("Stage: Fetch");
                if (pause) {
                    pause = false;
                    System.out.println("No instruction in Fetch Stage\n");
                } else if (PC < instructionCount) {
                    instruction = memory[PC];
                    System.out.println("Input:\n PC: " + PC + "\nOutput:\nInstruction: " + instruction + "(" + trace.get(instruction) + ")");
                } else {
                    instruction = null;
                    System.out.print("No instruction in Fetch Stage\n");
                }
            }
        }
    }

    static class Decode {
        //output:
        public static Integer opcode;
        public static Integer firstRegister;
        public static Integer secondRegister;
        public static Integer shamt;
        public static Integer Imd;
        public static Integer address;
        public static Integer destinationRegister;
        public static Integer destVal;
        public static StringBuilder toPrint;
        //input:
        static Integer instruction;

        public static void decode() {
            toPrint = new StringBuilder("Stage: Decode\n");
            if (cycles % 2 == 0) {
                instruction = Fetch.instruction;
            }
            if (instruction != null) {
                if (cycles % 2 == 0) {
                    opcode =  (instruction >>> 28); // bits31:28
                    destinationRegister =  (instruction & (0b00001111100000000000000000000000)) >> 23; // bits27:23
                    destVal = registers[destinationRegister];
                    firstRegister = registers[ (instruction & (0b00000000011111000000000000000000)) >> 18]; // bit22:18
                    secondRegister = registers[ (instruction & (0b00000000000000111110000000000000)) >> 13]; // bits17:13
                    shamt =  (instruction & (0b00000000000000000001111111111111)); // bits12:0
                    Imd =  (instruction & (0b00000000000000111111111111111111)); // bits17:0
                    int signOfIMD =  ((instruction & (0b00000000000000100000000000000000)) >> 17);
                    if (signOfIMD == 1) {
                        System.out.println("Balabiz0: " + Imd);
                        Imd |= 0b11111111111111000000000000000000;
                    }

                    address =(instruction & (0b00001111111111111111111111111111)); // bits27:0
                } else {
                    PC++;
                    
                    
                    
                    
                    
                }
                toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                toPrint.append("PC Value: ").append(PC).append("\n");
                toPrint.append("Input:\n");
                toPrint.append("Instruction: ").append(instruction).append("\n");
                toPrint.append("Output:\n");
                toPrint.append("Opcode: ");
                toPrint.append(opcode).append("\n");
                toPrint.append("Read1: ").append(firstRegister).append("\n");
                toPrint.append("Read2: ").append(secondRegister).append("\n");
                toPrint.append("Shift: ").append(shamt).append("\n");
                toPrint.append("Immediate: ").append(Imd).append("\n");
                toPrint.append("Address: ").append(address).append("\n");
                toPrint.append("Destination: ").append(destinationRegister).append("\n");
                toPrint.append("DestVal: ").append(destVal).append("\n");
            } else {
                toPrint.append("No instruction in Decode stage\n");
            }
            System.out.println(toPrint);
        }
    }

    static class Execute {
        //output
        public static Integer aluResult;
        public static StringBuilder toPrint;
        //input:
        static Integer instruction;
        public static Integer opcode;
        public static Integer firstRegister;
        public static Integer secondRegister;
        public static Integer shamt;
        public static Integer Imd;
        public static Integer address;
        public static Integer destinationRegister;
        public static Integer destVal;

        public static void execute() {
            toPrint = new StringBuilder("Stage: Execute\n");
            if (cycles % 2 == 0) {
                instruction = Decode.instruction;
            }
            if (instruction != null) {
                if (cycles % 2 == 0) {
                    destinationRegister = Decode.destinationRegister;
                    opcode = Decode.opcode; // bits31:28
                    firstRegister = Decode.firstRegister; // bit22:18
                    secondRegister = Decode.secondRegister; // bits17:13
                    shamt = Decode.shamt; // bits12:0
                    Imd = Decode.Imd; // bits17:0
                    address = Decode.address; // bits27:0
                    destVal = Decode.destVal;
                    switch (opcode) {
                        case 0: {//Add
                            aluResult = firstRegister + secondRegister;
                        }
                        break;

                        case 1: {//SUB
                            aluResult = firstRegister - secondRegister;
                        }
                        break;

                        case 2: {//MUL
                            aluResult = firstRegister * secondRegister;
                        }
                        break;

                        case 3://MOVI
                            aluResult = Imd;
                            break;

                        case 4: {//JEQ
                            if (destVal.equals(firstRegister)) {
                                System.out.println("Jumping");
                                PC = PC + Imd;
                                Decode.instruction = null;
                                Fetch.instruction = null;
                                Fetch.pause = true;
                            }
                            aluResult = 0;

                        }
                        break;

                        case 5://AND
                            aluResult = firstRegister & secondRegister;
                            break;

                        case 6://XORI
                            aluResult = firstRegister ^ Imd;
                            break;

                        case 7://Jump
                            System.out.println("Jumping :D");
                            PC = address;
                            Decode.instruction = null;
                            Fetch.instruction = null;
                            Fetch.pause = true;
                            aluResult = 0;
                            break;

                        case 8:// LSL
                            aluResult = firstRegister << shamt;
                            break;
                        case 9:// LSR
                            aluResult = firstRegister >> shamt;
                            break;

                        case 10://MOVR
                        case 11://MOVM
                            aluResult = firstRegister + Imd;
                            break;
                    }
                }
                toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                toPrint.append("Input:\n");
                toPrint.append("Opcode: ").append(opcode).append("\n");
                toPrint.append("Read1: ").append(firstRegister).append("\n");
                toPrint.append("Read2: ").append(secondRegister).append("\n");
                toPrint.append("Shift: ").append(shamt).append("\n");
                toPrint.append("Immediate: ").append(Imd).append("\n");
                toPrint.append("Address: ").append(address).append("\n");
                toPrint.append("Destination: ").append(destinationRegister).append("\n");
                toPrint.append("DestVal: ").append(destVal).append("\n");
                toPrint.append("Output:\nAlu Result: ").append(aluResult).append("\n");
            } else {
                toPrint.append("No instruction in Execute stage\n");
            }
            System.out.println(toPrint);
        }
    }

    static class Memory {
        //Input
        static Integer instruction;
        static Integer opcode, aluResult, destinationRegister, destVal;
        //output
        static Integer memResult;

        static StringBuilder toPrint;

        public static void memory() {
            if (cycles % 2 == 0) {
                instruction = Execute.instruction;

                toPrint = new StringBuilder("Stage: Memory\n");
                if (instruction != null) {
                    aluResult = Execute.aluResult;
                    opcode = Execute.opcode;
                    destinationRegister = Execute.destinationRegister;
                    destVal = Execute.destVal;
                    toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                    toPrint.append("\n").append("Input:\n");
                    toPrint.append("Opcode: ").append(opcode).append("\n");
                    toPrint.append("Alu Result: ").append(aluResult).append("\n");
                    toPrint.append("Register Data to write in memory(if needed):").append(destVal).append("\n");
                    toPrint.append("Output:\n");
                    if (aluResult < 2048 && aluResult >= 0) {
                        toPrint.append("Read data: ").append(memory[aluResult]).append("\n");
                    }
                    switch (opcode) {
                        case 10:
                            memResult =  memory[aluResult];
                            break;

                        case 11:
                            toPrint.append("Memory["+aluResult+"] <-- "+destVal);
                            memory[aluResult] = destVal;
                            break;
                    }
                } else {
                    toPrint.append("No instruction in Memory stage\n");
                }
                System.out.println(toPrint);
            }

        }


    }

    static class WriteBack {
        //input
        static Integer instruction;
        static Integer opcode, aluResult, memResult, destinationRegister;
        //no Output

        static StringBuilder toPrint;

static void writeBack() {
            if (cycles % 2 == 1) {
                instruction = Memory.instruction;
                toPrint = new StringBuilder("Stage: Write Back\n");
                if (instruction != null) {
                    opcode = Memory.opcode;
                    aluResult = Memory.aluResult;
                    memResult = Memory.memResult;
                    destinationRegister = Memory.destinationRegister;

                    toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                    toPrint.append("Input:" + "\n");
                    toPrint.append("Opcode: " + opcode + "\n");
                    toPrint.append("ALU Result: " + aluResult + "\n");
                    toPrint.append("Input from memory: " + memResult + "\n");
                    toPrint.append("Index of destination Register: " + destinationRegister + "\n");
                    switch (opcode) {
                        case 4:
                        case 7:
                        case 11:
                            toPrint.append("No Register Updated" + "\n");
                            return;
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9://Alu Result
                            registers[destinationRegister] = aluResult;
                            toPrint.append("R" + destinationRegister + " <--  " + aluResult + ((destinationRegister != 0) ? "" : "(No change, value remains 0)") + "\n");
                            return;
                        case 10://Memory Result
                            registers[destinationRegister] = memResult;
                            toPrint.append("R" + destinationRegister + " <--  " + memResult + ((destinationRegister != 0) ? "" : "(No change, value remains 0)") + "\n");
                    }
                } else {
                    toPrint.append("No Instruction in Write back Stage\n");
                }
                System.out.println(toPrint);
            }
        }
    }
}


//




