package ru.julie;

public class RedBlackTree {
    private Node root;

    // Добавление нового элемента
    public void add(int value) {
        root = addRecursive(root, value);
        root.isRed = false;
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }

        // Балансировка после добавления элемента
        if (isRed(current.right) && !isRed(current.left)) {
            current = rotateLeft(current);
        }
        if (isRed(current.left) && isRed(current.left.left)) {
            current = rotateRight(current);
        }
        if (isRed(current.left) && isRed(current.right)) {
            flipColors(current);
        }

        return current;
    }

    // Проверка, является ли узел красным (или null)
    private boolean isRed(Node node) {
        return node != null && node.isRed;
    }

    // Левый малый поворот
    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.isRed = node.isRed;
        node.isRed = true;
        return newRoot;
    }

    // Правый малый поворот
    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.isRed = node.isRed;
        node.isRed = true;
        return newRoot;
    }

    // Смена цвета
    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        node.left.isRed = !node.left.isRed;
        node.right.isRed = !node.right.isRed;
    }

    // Вывод дерева в виде диаграммы
    public void printTree() {
        printTreeRecursive(root, "", true);
    }
    private void printTreeRecursive(Node current, String indent, boolean last) {
        if (current != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("└─");
                indent += "  ";
            } else {
                System.out.print("├─");
                indent += "│ ";
            }

            String color = current.isRed ? "\u001B[31m" : "\u001B[0m";
            System.out.println(color + current.value + "\u001B[0m");

            printTreeRecursive(current.left, indent, false);
            printTreeRecursive(current.right, indent, true);
        }
    }

    // Пример использования
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.add(7);
        tree.add(3);
        tree.add(5);
        tree.add(10);
        tree.add(7);
        tree.add(8);
        tree.add(11);
        tree.add(6);
        tree.add(2);
        tree.add(15);
        tree.add(13);

        tree.printTree();
    }
}

