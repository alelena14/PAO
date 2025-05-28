import service.Service;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Service service;

    static {
        try {
            service = new Service();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\n🎮 === RPG Menu ===");
            System.out.println("1. Create a new character");
            System.out.println("2. View all characters");
            System.out.println("3. Choose current character");
            System.out.println("4. View current character inventory");
            System.out.println("5. Equip weapon");
            System.out.println("6. Upgrade/Enhance weapon");
            System.out.println("7. Buy healing potion");
            System.out.println("8. Buy a weapon");
            System.out.println("9. Sell item from inventory");
            System.out.println("10. Start a battle");
            System.out.println("11. View battle victories");
            System.out.println("12. View battle types");
            System.out.println("13. View enemy info");
            System.out.println("14. View current character stats");
            System.out.println("15. Show available character types");
            System.out.println("16. Exit");

            System.out.print("➡️ Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> service.newCharacter();
                case 2 -> service.viewAllCharacters();
                case 3 -> Service.chooseCurrentCharacter();
                case 4 -> service.viewInventory();
                case 5 -> service.equipWeapon();
                case 6 -> service.upgradeWeapon();
                case 7 -> service.buyHealingPotion();
                case 8 -> service.buyWeapon();
                case 9 -> service.sellItemFromInventory();
                case 10 -> service.newBattle();
                case 11 -> service.viewBattleVictories();
                case 12 -> service.displayBattleTypes();
                case 13 -> service.getEnemyInfo();
                case 14 -> Service.getInfo(service.getCurrentCharacter());
                case 15 -> service.showCharacterInfo();
                case 16 -> {
                    running = false;
                    System.out.println("👋 Goodbye!");
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }


}
