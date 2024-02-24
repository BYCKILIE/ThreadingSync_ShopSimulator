public class Main {
    private static class ManualSetup {
        public static void main(String[] args) {
            new Setup();
        }
    }

    private static class Test1 {
        public static void main(String[] args) {
            new SimulationFrame(4, 2, 60, new int[]{2, 30}, new int[]{2, 4}, "1");
        }
    }

    private static class Test2 {
        public static void main(String[] args) {
            new SimulationFrame(50, 5, 60, new int[]{2, 40}, new int[]{1, 7}, "2");
        }
    }
    private static class Test3 {
        public static void main(String[] args) {
            new SimulationFrame(1000, 20, 200, new int[]{10, 100}, new int[]{3, 9}, "3");
        }
    }

}