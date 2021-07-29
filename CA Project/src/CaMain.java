import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;


public class CaMain {
    static long[] memory = new long[2048];
    static int[] registers = new int[32];
    static Hashtable<Long,String> trace = new Hashtable<>();
    static Integer PC = 0;
    static int instructionCount = 0;
    static Long instruction = null;
    static Long InExecute = null, InMemory = null, InWrite = null;
    static Integer ExwrtTem = null, EXread1 = null, EXread2 = null, EXImmed = null, EXopcode = null, EXaddress = null,
            EXshamt = null;
    static Integer MEMopcode = null, MEMALURes = null, MEMwritedata = null;
    static Integer WBopcode = null, WBdestinationR = null, WBmemResult = null, WBAlu = null;
    static int cycles = 1;
    static boolean keyToExecute = false;



    public static void main(String[] args) {
        readFile("Test2");
//        instructionFetch();
//        cycles++;
//        System.out.println(cycles+"______________________________________");
//
//        execute();
//        instructionDecode();
//        cycles++;
//        System.out.println(cycles+"______________________________________");
//
//        execute();
//        instructionDecode();
        
//        registers[1]=1;
//        registers[2]=2;
//        memory[4]=10;
        
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
            System.out.println("______________________________________");
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
            sb.append(")");
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

    private static String intToBinary(long number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 32; i++) {
            sb.append((number % 2 + 2) % 2);
            number /= 2;
        }
        sb.reverse();
        return sb.toString();
    }

    public static void readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
            int i = 0;
            while (br.ready()) {
                long inserted = 0;
                String s = br.readLine();
                StringTokenizer st = new StringTokenizer(s, " ");
                switch (st.nextToken()) {
                    case "ADD":
                        System.out.println("Add");
                        inserted |= 0 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "SUB":
                        System.out.println("Sub");
                        inserted |= 1 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "MUL":
                        System.out.println("mul");
                        inserted |= 2 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "MOVI":
                        System.out.println("movi");
                        inserted |= 3 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= 0 << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "JEQ":
                        System.out.println("jeq");
                        inserted |= 4 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "AND":
                        System.out.println("and");

                        inserted |= 5 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= regValue(st.nextToken()) << 13;
                        break;
                    case "XORI":
                        System.out.println("xori");

                        inserted |= 6 << 28;
                        inserted |= regValue(st.nextToken()) << 23;
                        inserted |= regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "JMP":

                        System.out.println("jmp");

                        inserted |= 7 << 28;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "LSL":
                        System.out.println("lsl");

                        inserted |= (long) 8 << 28;
                        inserted |= (long) regValue(st.nextToken()) << 23;
                        inserted |= (long) regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "LSR":
                        System.out.println("lsr");

                        inserted |= (long) 9 << 28;
                        inserted |= (long) regValue(st.nextToken()) << 23;
                        inserted |= (long) regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "MOVR":
                        System.out.println("movr");

                        inserted |= (long) 10 << 28;
                        inserted |= (long) regValue(st.nextToken()) << 23;
                        inserted |= (long) regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                    case "MOVM":
                        System.out.println("movl");

                        inserted |= (long) 11 << 28;

                        inserted |= (long) regValue(st.nextToken()) << 23;
                        inserted |= (long) regValue(st.nextToken()) << 18;
                        inserted |= Integer.parseInt(st.nextToken());
                        break;
                }
                trace.put(inserted,s);
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
        static Long instruction;

        static boolean pause = false;

        public static void fetch() {
            if (cycles % 2 == 1) {
                if (pause) {
                    pause = false;
                } else if (PC < instructionCount) {
                    instruction = memory[PC];
                } else {
                    instruction = null;
                }
                System.out.print("Stage: Fetch\n Input: PC= " + PC + "\n Output: Instruction= " + instruction+ "\n" + "\n");
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
        public static StringBuilder toPrint;
        public static Integer destVal;
        //input:
        static Long instruction;

        public static void decode() {
            toPrint = new StringBuilder("Stage: Decode\n");
            if (cycles % 2 == 0) {
                instruction = Fetch.instruction;
            }
            if (instruction != null) {
                if (cycles % 2 == 0) {
                    opcode = (int) (instruction >> 28); // bits31:28
                    destinationRegister = (int) (instruction & (0b00001111100000000000000000000000)) >> 23; // bits27:23
                    destVal=registers[destinationRegister];    
                    firstRegister = registers[(int) (instruction & (0b00000000011111000000000000000000)) >> 18]; // bit22:18
                    secondRegister = registers[(int) (instruction & (0b00000000000000111110000000000000)) >> 13]; // bits17:13
                    shamt = (int) (instruction & (0b00000000000000000001111111111111)); // bits12:0
                    Imd = (int) (instruction & (0b00000000000000111111111111111111)); // bits17:0
                    address = (int) (instruction & (0b00001111111111111111111111111111)); // bits27:0


                } else {
                    PC++;
                }
                toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
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
            } else {
                opcode = null; // bits31:28
                destinationRegister = null; // bits27:23
                firstRegister = null; // bit22:18
                secondRegister = null; // bits17:13
                shamt = null; // bits12:0
                Imd = null; // bits17:0
                address = null; // bits27:0
                toPrint.append("No instruction in Decode stage\n");
            }
            System.out.println(toPrint);
        }
    }

    static class Execute {
        //input:
        public static Long instruction;
        public static Integer opcode;
        public static Integer firstRegister;
        public static Integer secondRegister;
        public static Integer shamt;
        public static Integer Imd;
        public static Integer address;
        public static Integer destinationRegister;
        public static Integer destVal;

        //output
        public static Integer aluResult;

        public static StringBuilder toPrint;

        public static void execute() {
            toPrint = new StringBuilder("Stage: Execute\n");
            if (cycles % 2 == 0) {
                instruction = Decode.instruction;
            }
            if (instruction != null) {
                toPrint.append("");
                if (cycles % 2 == 0) {
                    destinationRegister = Decode.destinationRegister;
                    opcode = Decode.opcode; // bits31:28
                    firstRegister = Decode.firstRegister; // bit22:18
                    secondRegister = Decode.secondRegister; // bits17:13
                    shamt = Decode.shamt; // bits12:0
                    Imd = Decode.Imd; // bits17:0
                    address = Decode.address; // bits27:0
                    destVal=Decode.destVal;
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
                            if (secondRegister.equals(firstRegister)) {
                                PC = PC + Imd;
                                Decode.instruction = null;
                                Fetch.instruction = null;
                                Fetch.pause = true;
                                aluResult = 0;
                            }
                        }
                        break;

                        case 5://AND
                            aluResult = firstRegister & secondRegister;
                            break;

                        case 6://XORI
                            aluResult = firstRegister ^ Imd;
                            break;

                        case 7://Jump
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
                        	

                        case 11://MOVI
                            aluResult = firstRegister + Imd;
                            break;
                    }
                }
                toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                toPrint.append("Input:\n");
                toPrint.append("Output:\n");
                toPrint.append("Opcode: ").append(opcode).append("\n");
                toPrint.append("Read1: ").append(firstRegister).append("\n");
                toPrint.append("Read2: ").append(secondRegister).append("\n");
                toPrint.append("Shift: ").append(shamt).append("\n");
                toPrint.append("Immediate: ").append(Imd).append("\n");
                toPrint.append("Address: ").append(address).append("\n");
                toPrint.append("Destination: ").append(destinationRegister).append("\n");
                toPrint.append("Output:\nAlu Result: ").append(aluResult).append("\n");
            } else {
                toPrint.append("No instruction in Execute stage\n");
            }
            System.out.println(toPrint);
        }
    }

    static class Memory {
        //Input
        static Long instruction;
        static Integer opcode, aluResult, destinationRegister;
        public static Integer destVal;
        //output
        static Integer memResult;

        static StringBuilder toPrint;

        public static void memory() {
            if (cycles % 2 == 0) {
                instruction = Execute.instruction;

                toPrint = new StringBuilder("Stage: Memory\n");
                if (instruction != null) {
                    aluResult=Execute.aluResult;
                    opcode = Execute.opcode;
                    destinationRegister = Execute.destinationRegister;
                    destVal = Execute.destVal;
                    switch (opcode) {
                        case 10:
                            memResult = (int) memory[aluResult];
                            break;

                        case 11:
                            memory[aluResult] = destVal;
                            break;
                    }

                    toPrint.append("Instruction: ").append(instruction).append("(").append(trace.get(instruction)).append(")").append("\n");
                    toPrint.append("\n").append("Input:\n");
                    toPrint.append("Opcode: ").append(opcode).append("\n");
                    toPrint.append("Alu Result: ").append(aluResult).append("\n");
                    toPrint.append("Register Dara to write in memory(if needed):").append(destVal).append("\n");
                    toPrint.append("Output:\n");
                    toPrint.append("Read data: ").append(memory[aluResult]).append("\n");
                } else {
                    toPrint.append("No instruction in Memory stage\n");
                }
                System.out.println(toPrint);
            }

        }


    }

    static class WriteBack {
        //input
        static Long instruction;
        static Integer opcode, aluResult,memResult,destinationRegister;
        //no Output

        static StringBuilder toPrint;

        static void writeBack() {
            if (cycles % 2 == 1) {
                instruction=Memory.instruction;
                opcode = Memory.opcode;
                aluResult = Memory.aluResult;
                memResult = Memory.memResult;
                destinationRegister = Memory.destinationRegister;
                toPrint = new StringBuilder("Stage: Write Back\n");
                if(instruction!=null){
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
                        case 9:
                            registers[destinationRegister] = aluResult;
                            toPrint.append("R" + destinationRegister + " <--  " + aluResult + ((destinationRegister != 0) ? "" : "(No change, value remains 0)") + "\n");
                            return;
                        case 10:
                            registers[destinationRegister] = memResult;
                            toPrint.append("R" + destinationRegister + " <--  " + memResult + ((destinationRegister != 0) ? "" : "(No change, value remains 0)") + "\n");
                    }
                }else{
                    toPrint.append("No Instruction in Write back Stage\n");
                }
                System.out.println(toPrint);
            }
        }
    }
}


//




