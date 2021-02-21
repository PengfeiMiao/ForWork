package com.mpf.study.util;

import java.util.Random;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/19 16:58
 * redis跳表实现
 */
public class SkipList {
    private static final int MAX_LEVEL = 16;
    private int levelCount = 1;
    private Node head = new Node();
    private Random random = new Random();

    public Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        }
        return null;
    }

    public void insert(int value) {
        //获取数据链表
        Node p = head;
        int level = randomLevel();
        //待插入节点赋值
        Node node = new Node();
        node.data = value;
        node.maxLevel = level;
        //各级的最大节点数组（与待插入节点比较）
        Node update[] = new Node[level];
        //记录每层节点的最大值并保存在update数组
        for (int i = level; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                //p右移为当前层索引小于value的最大值
                p = p.forwards[i];
            }
            //p保存至update
            update[i] = p;
        }
        //各级节点插入node
        for (int i = 0; i < level; i++) {
            node.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = node;
        }
        //更新索引层数
        if (levelCount < level) {
            levelCount = level;
        }
    }

    public void delete(int value) {
        Node[] deleteNode = new Node[MAX_LEVEL];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            deleteNode[i] = p;
        }
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; i--) {
                if (deleteNode[i] != null && deleteNode[i].forwards[i].data == value) {
                    deleteNode[i].forwards[i] = deleteNode[i].forwards[i].forwards[i];
                }
            }
        }
    }

    public void printAll() {
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.print(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    private int randomLevel() {
        int level = 0;
        for (int i = 0; i < MAX_LEVEL; i++) {
            //如果是奇数，索引层数+1
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    class Node {
        private int data;
        //前置节点数组
        private Node[] forwards = new Node[MAX_LEVEL];
        private int maxLevel;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{data: ");
            sb.append(data);
            sb.append("; level: ");
            sb.append(maxLevel);
            sb.append(" }");
            return sb.toString();
        }
    }


}
