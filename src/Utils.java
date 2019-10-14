import java.util.*;

// Utils class to store helper functions
class Utils {
  // EFFECT: Sorts the provided list according to the given comparator
  <T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
    // Create a temporary array
    ArrayList<T> temp = new ArrayList<T>();
    // Make sure the temporary array is exactly as big as the given array
    for (int i = 0; i < arr.size(); i = i + 1) {
      temp.add(arr.get(i));
    }
    mergesortHelp(arr, temp, comp, 0, arr.size());
  }

  // EFFECT: Sorts the provided list in the region [loIdx, hiIdx)
  //         Modifies both lists in the range [loIdx, hiIdx)
  <T> void mergesortHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
                         int loIdx, int hiIdx) {
    // Step 0: stop when finished
    if (hiIdx - loIdx <= 1) {
      return; // nothing to sort
    }
    // Step 1: find the middle index
    int midIdx = (loIdx + hiIdx) / 2;
    // Step 2: recursively sort both halves
    mergesortHelp(source, temp, comp, loIdx, midIdx);
    mergesortHelp(source, temp, comp, midIdx, hiIdx);
    // Step 3: merge the two sorted halves
    merge(source, temp, comp, loIdx, midIdx, hiIdx);
  }

  // Merges the two sorted regions [loIdx, midIdx) and [midIdx, hiIdx) from source
  // into a single sorted region according to the given comparator
  // EFFECT: modifies the region [loIdx, hiIdx) in both source and temp
  <T> void merge(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
                 int loIdx, int midIdx, int hiIdx) {
    int curLo = loIdx;   // where to start looking in the lower half-list
    int curHi = midIdx;  // where to start looking in the upper half-list
    int curCopy = loIdx; // where to start copying into the temp storage
    while (curLo < midIdx && curHi < hiIdx) {
      if (comp.compare(source.get(curLo), source.get(curHi)) <= 0) {
        // the value at curLo is smaller, so it comes first
        temp.set(curCopy, source.get(curLo));
        curLo = curLo + 1; // advance the lower index
      }
      else {
        // the value at curHi is smaller, so it comes first
        temp.set(curCopy, source.get(curHi));
        curHi = curHi + 1; // advance the upper index
      }
      curCopy = curCopy + 1; // advance the copying index
    }
    // copy everything that's left -- at most one of the two half-lists still has items in it
    while (curLo < midIdx) {
      temp.set(curCopy, source.get(curLo));
      curLo = curLo + 1;
      curCopy = curCopy + 1;
    }
    while (curHi < hiIdx) {
      temp.set(curCopy, source.get(curHi));
      curHi = curHi + 1;
      curCopy = curCopy + 1;
    }
    // copy everything back from temp into source
    for (int i = loIdx; i < hiIdx; i = i + 1) {
      source.set(i, temp.get(i));
    }
  }
}

// interface for a Comparator
interface IComparator<T> extends Comparator<T> {
}

// to compare the weight of two Edges
class HeavierThan implements IComparator<Edge> {
  public int compare(Edge left, Edge right) {
    if (left.weight == right.weight) {
      return 0;
    }
    else if (left.weight > right.weight) {
      return 1;
    }
    else {
      return -1;
    }
  }
}
