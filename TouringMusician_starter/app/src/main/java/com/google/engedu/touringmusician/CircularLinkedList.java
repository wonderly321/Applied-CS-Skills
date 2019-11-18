/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;
import android.util.Log;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
    }

    Node head;
    private void insertHelper(Node node){
        head = node;
        head.next = head;
        head.prev = head;
    }

    private int getSize() {
        Node cur = head;
        if(cur == null) return 0;
        if(cur.next == head) return 1;
        cur = cur.next;
        int ret = 1;
        while(cur != head) {
            ret += 1;
            cur=cur.next;
        }
        return ret;
    }

    public void insertBeginning(Point p) {
        Node node = new Node();
        node.point = p;
        if(head == null){
            insertHelper(node);
        }
        else{
            node.next = head;
            node.prev = head.prev;
            head.prev.next = node;
            head.prev = node;
            head = node;
        }
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        if(head == null) return total;
        Node prev,cur;
        prev = head;
        cur = prev.next;
        while(cur!= head){
            total+=distanceBetween(prev.point,cur.point);
            prev = cur;
            cur =prev.next;
        }
        return total;
    }

    public void insertNearest(Point p) {
        Node newnode = new Node();
        newnode.point = p;
        if(head == null){
            insertHelper(newnode);
        }
        else{
            Node nrst = head, cur = head;
            float dis = distanceBetween(cur.point,p);
            cur = cur.next;
            while(cur!=head){
                if(distanceBetween(cur.point,p)<dis){
                    nrst = cur;
                    dis = distanceBetween(cur.point,p);
                }
                cur = cur.next;
            }
            newnode.prev = nrst;
            newnode.next = nrst.next;
            nrst.next.prev = newnode;
            nrst.next = newnode;
        }

        Log.d("DEBUG", String.format("Size : %d", getSize()));
    }

    public void insertSmallest(Point p) {
        Node newnode = new Node();
        newnode.point = p;
        if(head == null){
            insertHelper(newnode);
        }
        else{
            Node smst = head, cur =head, next = head.next;
            if(next == head){
               head.next = newnode;
               newnode.next = head;
               head.prev = newnode;
               newnode.prev = head;
            }
            else{
                float smallest = distanceBetween(smst.point,newnode.point)+distanceBetween(newnode.point,next.point)-distanceBetween(smst.point,next.point);
                cur = next;
                next = next.next;
                while(cur!=head){
                    float tmp = distanceBetween(cur.point,newnode.point)+distanceBetween(newnode.point,next.point)-distanceBetween(cur.point,next.point);

                    if(tmp < smallest){
                        smallest = tmp;
                        smst = cur;
                    }
                    cur = next;
                    next = next.next;
                }
                newnode.prev = smst;
                newnode.next = smst.next;
                smst.next.prev = newnode;
                smst.next = newnode;

                cur = head.next;
                float dis = 0;
                Node ori = head;
                while(cur != ori) {
                    if(distanceBetween(cur.point, cur.prev.point) > dis) {
                        dis = distanceBetween(cur.point, cur.prev.point);
                        head = cur;
                    }
                    if(distanceBetween(cur.point, cur.next.point) > dis) {
                        dis = distanceBetween(cur.point, cur.next.point);
                        head = cur.next;
                    }
                    cur = cur.next;
                }

            }
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
