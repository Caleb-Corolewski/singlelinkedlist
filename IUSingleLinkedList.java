import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Single linked list implementation of an indexed unsorted list.
 * @author Caleb Corolewski
 * @Date June 2025
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T>
{
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount;

    /**
     * Default constructor for a single linked list
     */
    public IUSingleLinkedList(){
        head = tail = null;
        size = 0;
        modCount = 0;
    }
     

    /*
     * Adds the specified element to the front of this list. 
     *
     * @param element the element to be added to the front of this list    
     */
    @Override
    public void addToFront(T element) 
    {
        Node<T> newNode = new Node<T>(element);
        if(size == 0)
        {
            tail = newNode;
        }
        newNode.setNext(head);
        head = newNode;
        size++;
        modCount++;
    }

    /* 
     * Adds the specified element to the rear of this list. 
     *
     * @param element the element to be added to the rear of this list    
     */
    @Override
    public void addToRear(T element) 
    {
        Node<T> newNode = new Node<T>(element);
        if(size > 0){
            tail.setNext(newNode);
        }else{
            head = newNode;
        }
        tail = newNode;
        size++;
        modCount++;
    }

    /*
     * Adds the specified element to the rear of this list. 
     *
     * @param element  the element to be added to the rear of the list    
     */
    @Override
    public void add(T element) 
    {
        addToRear(element);
    }

    /*
     * Adds the specified element after the first element of the list matching the specified target. 
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element will be added after
     * @throws NoSuchElementException if target element is not in this list
     */
    @Override
    public void addAfter(T element, T target) 
    {
        if(indexOf(target) == -1)
        {
            throw new NoSuchElementException();
        }
        Node<T> newNode = new Node<T>(element);
        Node<T> currentNode = head;
        while(!currentNode.getElement().equals(target) && currentNode != null)
        {
            currentNode = currentNode.getNext();
        }
        newNode.setNext(currentNode.getNext());
        currentNode.setNext(newNode);
        if(currentNode == tail)
        {
            tail = newNode;
        }
        size++;
        modCount++;
    }

    /* 
     * Inserts the specified element at the specified index. 
     * 
     * @param index   the index into the array to which the element is to be inserted. 
     * @param element the element to be inserted into the array
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    @Override
    public void add(int index, T element) 
    {
        if(index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<T>(element);
        if (index == 0) 
        {
            newNode.setNext(head);
            head = newNode;
            if (size == 0) 
            {
                tail = newNode;
            }
        } else 
        {
            Node<T> prevNode = head;
            for (int i = 0; i < index - 1; i++) 
            {
                prevNode = prevNode.getNext();
            }
            newNode.setNext(prevNode.getNext());
            prevNode.setNext(newNode);
            if (prevNode == tail) 
            {
                tail = newNode;
            }
        }
        size++;
        modCount++;
    }

    /*
     * Removes and returns the first element from this list. 
     * 
     * @return the first element from this list
     * @throws NoSuchElementException if list contains no elements
     */
    @Override
    public T removeFirst() 
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        T retVal = head.getElement();
        if(size == 1)
        {
            head = tail = null;
        } else
        {
            head = head.getNext();  
        }        
        size--;
        modCount++;
        return retVal;
    }

    /*
     * Removes and returns the last element from this list. 
     *
     * @return the last element from this list
     * @throws NoSuchElementException if list contains no elements
     */
    @Override
    public T removeLast() 
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        if(size == 1)
        {
            head = tail = null;
        } else
        {
            Node<T> currentNode = head;
            while (currentNode.getNext() != tail && currentNode.getNext() != null)
            {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(null);
            tail = currentNode;
            
        }
        size--;
        modCount++;
        return retVal;
    }

    /*
     * Removes and returns the first element from the list matching the specified element.
     *
     * @param element the element to be removed from the list
     * @return removed element
     * @throws NoSuchElementException if element is not in this list
     */
    @Override
    public T remove(T element) 
    {
        if(indexOf(element) == -1)
        {
            throw new NoSuchElementException();
        }
        T retVal = element;
        if(head.getElement().equals(element))
        {
            head = head.getNext();
            if(size == 1)
            {
                tail = head;
            }
        } else
        {
            Node<T> prevNode = head;
            Node<T> currentNode = head.getNext();
            while(currentNode != null && !currentNode.getElement().equals(element))
            {
                prevNode = currentNode;
                currentNode = currentNode.getNext();
            }
            prevNode.setNext(currentNode.getNext());
            if(currentNode == tail)
            {
                tail = prevNode;
            }
        }
        size--;
        modCount++;
        return retVal;
    }

    /*  
     * Removes and returns the element at the specified index. 
     *
     * @param index the index of the element to be retrieved
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public T remove(int index) 
    {
        if(index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> prevNode = head;
        T retVal = null;
        if(index == 0)
        {
            retVal = head.getElement();
            head = head.getNext();
            prevNode = null;
            if(size == 1)
            {
                tail = head;
            }
            size--;
            modCount++;
            return retVal;
        }
        for(int i = 0; i < index-1; i++)
        {
            prevNode = prevNode.getNext();
        }
        if(prevNode.getNext() == tail)
        {
            retVal = tail.getElement();
            tail = prevNode;
            tail.setNext(null);
        } else
        {
            retVal = prevNode.getNext().getElement();
            prevNode.setNext(prevNode.getNext().getNext());
        }
        size--;
        modCount++;
        return retVal;
    }

    /*
     * Replace the element at the specified index with the given element. 
     *
     * @param index   the index of the element to replace
     * @param element the replacement element to be set into the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public void set(int index, T element) 
    {
        if(index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for(int i = 0; i < index; i++)
        {
            currentNode = currentNode.getNext();
        }
        currentNode.setElement(element);
        modCount++;
    }

    /*
     * Returns a reference to the element at the specified index. 
     *
     * @param index  the index to which the reference is to be retrieved from
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public T get(int index) 
    {
        if(index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for(int i = 0; i < index; i++)
        {
            currentNode = currentNode.getNext();
        }
        return currentNode.getElement();
    }

    /*  
     * Returns the index of the first element from the list matching the specified element. 
     *
     * @param element  the element for the index is to be retrieved
     * @return the integer index for this element or -1 if element is not in the list    
     */
    @Override
    public int indexOf(T element) 
    {
        Node<T> currentNode = head;
        int currentIndex = 0;
        int returnIndex =-1;
        while(currentNode != null && returnIndex < 0)
        {
            if(currentNode.getElement().equals(element))
            {
                returnIndex = currentIndex;
            } else 
            {
                currentIndex++;
                currentNode = currentNode.getNext();
            }
        }
        return returnIndex;
    }

    /* 
     * Returns a reference to the first element in this list. 
     *
     * @return a reference to the first element in this list
     * @throws NoSuchElementException if list contains no elements
     */
    @Override
    public T first() 
    {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    /* 
     * Returns a reference to the last element in this list. 
     *
     * @return a reference to the last element in this list
     * @throws NoSuchElementException if list contains no elements
     */
    @Override
    public T last() 
    {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    /* 
     * Returns true if this list contains the specified target element. 
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element, else false
     */
    @Override
    public boolean contains(T target) 
    {
        return indexOf(target) > -1;
    }

    /*  
     * Returns true if this list contains no elements. 
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    /* 
     * Returns the number of elements in this list. 
     *
     * @return the integer representation of number of elements in this list
     */
    @Override
    public int size() 
    {
        return size;
    }

    /* 
     * Returns a string representation of this list. 
     *
     * @return a string representation of this list
     */
    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder("[");
        Node<T> currentNode = head;
        for(int i = 0; i < size; i++)
        {   if(currentNode == null)
            {
                break;
            }
            if(i < size - 1)
            {
                string.append(currentNode.getElement());
                string.append(",");
            }if (i == size -1) {
                string.append(currentNode.getElement());                
            }
            currentNode = currentNode.getNext();
        }
		string.append("]");
		
		return string.toString();
    }

    /* 
     * Returns an Iterator for the elements in this list. 
     *
     * @return an Iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() 
    {
        return new SLLIterator();
    }

    /*  
     * Returns a ListIterator for the elements in this list. 
     *
     * @return a ListIterator over the elements in this list
     *
     * @throws UnsupportedOperationException if not implemented
     */
    @Override
    public ListIterator<T> listIterator() 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    /* 
     * Returns a ListIterator for the elements in this list, with
     * the iterator positioned before the specified index. 
     *
     * @return a ListIterator over the elements in this list
     *
     * @throws UnsupportedOperationException if not implemented
     */
    @Override
    public ListIterator<T> listIterator(int startingIndex) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }
    /*
     * Iterator for SLL
     */
    private class SLLIterator implements Iterator<T> 
    {
        private Node<T> nextNode;
        private Node<T> lastReturned;
        private int iterModCount;
        private boolean canRemove;

        /**
         * Default constructor for single linked list's iterator
         */
        public SLLIterator()
        {
            nextNode = head;
            lastReturned = null;
            iterModCount = modCount;
            canRemove = false;
        }   

    /*
     * Returns true if the list has a next value
     * @returns boolean 
     */
    public boolean hasNext()
    {
        return nextNode != null;
    }

    /*
     * Method for finding the next node
     * @returns the next node in the list
     */
    public T next() 
    {
        if (modCount != iterModCount) 
        {
            throw new ConcurrentModificationException();
        }
        if (!hasNext()) 
        {
            throw new NoSuchElementException();
        }
        lastReturned = nextNode;
        nextNode = nextNode.getNext();
        canRemove = true;
        return lastReturned.getElement();
    }

    /*
     * removes the last returned node if canRemove = true
     */
    public void remove() 
    {
        if (modCount != iterModCount) 
        {
            throw new ConcurrentModificationException();
        }
        if (!canRemove) 
        {
            throw new IllegalStateException();
        }
        Node<T> prevNode = head;
        if(lastReturned == head)
        {
            head = head.getNext();
            if(head == null)
            {
                tail = null;
            }
        } else 
            {
                
                while(prevNode != null && prevNode.getNext()!= lastReturned)
                {
                 prevNode = prevNode.getNext();
                }
                if (prevNode != null)
                {
                    prevNode.setNext(lastReturned.getNext());               
            
                    if (lastReturned == tail)
                    {
                        tail = prevNode;
                    }
                }
            }
        lastReturned = null;  
        modCount++;
        iterModCount++;
        size--;
        canRemove = false;
    }
}
}
