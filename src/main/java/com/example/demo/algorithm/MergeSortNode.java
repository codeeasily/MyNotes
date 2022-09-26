package com.example.demo.algorithm;

/**
 * @date 2022/09/26 11:13
 */
public class MergeSortNode {
    public static void main(String[] args) {
        Node node = generateNode(1, 5, 7, 3, 2, 6, 4);
        System.out.println(node);
        Node sortedNode = mergeSort(node);
        System.out.println(sortedNode);
    }

    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node mid = findMid(head), next = mid.next;
        // 这一步很关键，将链表断开，防止死循环
        mid.next = null;
        Node left = mergeSort(head);
        Node right = mergeSort(next);
        // 左右两边都有序了，进行合并
        return merge(left, right);

    }

    public static Node generateNode(int... arr) {
        Node node = new Node(-1), cur = node;
        for (int i : arr) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        return node.next;
    }

    // 合并两个有序的链表
    public static Node merge(Node left, Node right) {
        Node head = new Node(-1), cur = head;
        while (left != null && right != null) {
            if (left.value < right.value) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        if (left != null) {
            cur.next = left;
        }
        if (right != null) {
            cur.next = right;
        }
        return head.next;
    }

    // 通过快慢指针确定中位的位置，快指针速度是慢指针的两倍，快指针到尾部的时候，慢指针刚好到中间
    public static Node findMid(Node head) {
        if (head == null) {
            return head;
        }
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}

class Node {
    public Node next;
    public int value;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node cur = this;
        while (cur != null) {
            sb.append(cur.value);
            if (cur.next != null) {
                sb.append("->");
            }
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
