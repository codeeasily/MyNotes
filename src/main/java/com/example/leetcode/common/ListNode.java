package com.example.leetcode.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author codeeasily
 * @date 2022/08/10 12:55
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[").append(val);
        ListNode node = next;
        while (node != null) {
            sb.append(",").append(node.val);
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
