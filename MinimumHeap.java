//Name: Sharath Chandra Bagur Suryanarayanaprasad Student ID: 800974802

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* Class MinimumHeap */
class MinimumHeap {
	/* The number of children each node has */
	private static final int d = 2;
	private int heapSize;
	private int hCapacity;
	private ArrayList<Vertex> heap;

	/* Constructor */
	public MinimumHeap(int hCapacity) {
		this.hCapacity = hCapacity;
		heapSize = 0;
		heap = new ArrayList<>();
		for (int count = 0; count < hCapacity; count++) {
			heap.add(new Vertex() {
				{
					dist = (float) -1.0;
				}
			});
		}
	}

	/* Function to check if heap is empty */
	public boolean isEmpty() {
		return heapSize == 0;
	}

	/* Check if heap is full */
	public boolean isFull() {
		return heapSize == heap.size();
	}

	/*
	 * Clear heap / public void makeEmpty() { heapSize = 0; }
	 * 
	 * /* Function to get index parent of i
	 */
	private int parent(int i) {
		return (i - 1) / d;
	}

	/* Function to get index of k th child of i */
	private int nthChild(int i, int k) {
		return d * i + k;
	}

	/*
	 * Function to insert element
	 */
	public void insert(Vertex x) {
		if (isFull())
			throw new NoSuchElementException("Overflow Exception");
		/** Percolate up **/
		heap.add(heapSize++, x);

		heapifyUp(heapSize - 1);
	}

	/* Function to find least element */
	public Vertex findMin() {
		if (isEmpty())
			throw new NoSuchElementException("Underflow Exception");
		return heap.get(0);
	}

	/* Function to delete min element */
	public Vertex deleteMin() {
		Vertex keyItem = heap.get(0);
		heapSize--;
		heap.remove(0);
		return keyItem;
	}

	/* Function to delete element at an index */
	public Vertex delete(int ind) {
		if (isEmpty())
			throw new NoSuchElementException("Underflow Exception");
		Vertex keyItem = heap.get(ind);
		heap.add(ind, heap.get(heapSize - 1));
		heapSize--;
		heapifyDown(ind);
		return keyItem;
	}

	/* Function heapifyUp */
	private void heapifyUp(int childInd) {
		Vertex tmp = heap.get(childInd);
		while (childInd > 0 && tmp.dist < heap.get(parent(childInd)).dist) {
			heap.add(childInd, heap.get(parent(childInd)));
			childInd = parent(childInd);
		}

		heap.add(childInd, tmp);
	}

	/* Function heapifyDown */
	private void heapifyDown(int ind) {
		int child;
		Vertex tmp = heap.get(ind);
		while (nthChild(ind, 1) < heapSize) {
			child = minChild(ind);
			if (heap.get(child).dist < tmp.dist)
				heap.add(ind, heap.get(child));
			else
				break;
			ind = child;
		}

		heap.add(ind, tmp);
	}

	/* Function to get smallest child */
	private int minChild(int ind) {
		int bChild = nthChild(ind, 1);
		int k = 2;
		int pos = nthChild(ind, k);
		while ((k <= d) && (pos < heapSize)) {
			if (heap.get(pos).dist < heap.get(bChild).dist)
				bChild = pos;
			pos = nthChild(ind, k++);
		}
		return bChild;
	}

	/* Function to print heap */
	public void printHeap() {
		System.out.print("\nHeap = ");
		for (int i = 0; i < heapSize; i++)
			System.out.print(heap.get(i) + " ");
		System.out.println();
	}
	
	public int returnIndex(Vertex v){
		if(heap.contains(v)){
			return heap.indexOf(v);
		}
		
		return -1;
	}
	
	public void update(Vertex v){
		delete(heap.indexOf(v));
		insert(v);
	}
}
