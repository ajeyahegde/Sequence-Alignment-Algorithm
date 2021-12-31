# Sequence-Alignment-Algorithm
Implementation of Algorithm to Sequence Alignment Problem with Dynamic Programming and Dynamic Programming in combination with Divide & Conquer(Memory Efficient) approach and comparison of Memory Usage and CPU time. 

**Summary:**

**Basic Version: Dynamic Programming**

Cases to consider while evaluating optimal value

1. If (Xm, Yn) is part of the the optimal solution,  then the size of the sub problem will be (m-1) * (n-1)
2. (Xm, Yn) is not a pair in the optimal solution
    
    a. Create a gap in Xm and deal with (X1...Xm-1) (Y1...Yn)
    
    b. Create a gap in Yn and deal with (X1...Xm) (Y1...Yn-1)

Cost to find the optimal value is O (mn) where m is the size of string X and n is the size of string Y. 
This version requires building up a 2D m*n array of optimal solutions to subproblems. Thus the space requirement of this version is O(mn).

**Enhanced/Optimized version: Combination of Divide and Conquer and Dynamic Programming**

Space requirement can be brought down to linear by increasing run time by at most an additional constant factor.
Using divide and conquer, we split string X in the middle and string Y at an optimal point. Then solve the problem recursively. When solved using divide and conquer, we don’t just get the optimal value but the optimal solution by concatenating the alignments of (XL, YL) and (XR, YR). 
To find the optimal split point: Dynamic Programming step:
  1. Check the alignment cost between the left side of X and all possible substrings of Y. 
  2. Check the cost of alignment between the right side of X and all possible substrings of Y. 

Works in O(mn) time using only O(m+n) space.
Using divide and conquer, size of the subproblems to compute decreases at each step
  First divide step requires     Cmn
  The next level down requires ½ Cmn
  The next level down requires ¼ Cmn
				.
				.
				.
  Adding up to: 		            2 Cmn = O(mn)

**Total Space: O(n) for computation and O(m+n) to store optimal alignment**

**Algorithm Description:**

Suppose we are given two strings X and Y, where X consists of the sequence of symbols x1, x2 . . . xm and Y consists of the sequence of symbols y1, y2 . . . yn. Consider the sets {1, 2, . . . , m} and {1, 2, . . . , n} as representing the different positions in the strings X and Y, and consider a matching of these sets; recall that a matching is a set of ordered pairs with the property that each item occurs in at most one pair. We say that a matching M of these two sets is an alignment if there are no “crossing” pairs: if (i, j), (i’ , j’ ) ∈ M and i < i’ , then j < j’ . Intuitively, an alignment gives a way of lining up the two strings, by telling us which pairs of positions will be lined up with one another.
  
Our definition of similarity will be based on finding the optimal alignment between X and Y, according to the following criteria. Suppose M is a given alignment between X and Y:

1. First, there is a parameter δe > 0 that defines a gap penalty. For each position of X or Y that is not matched in M — it is a gap — we incur a cost of δ.

2. Second, for each pair of letters p, q in our alphabet, there is a mismatch cost of αpq for lining up p with q. Thus, for each (i, j) ∈ M, we pay the appropriate mismatch
cost αxiyj for lining up xi with yj. One generally assumes that αpp = 0 for each letter p—there is no mismatch cost to line up a letter with another copy of itself—although this will not be necessary in anything that follows.

3. The cost of M is the sum of its gap and mismatch costs, and we seek an alignment of minimum cost.

**Values for Delta and Alphas**

Values for α’s are as follows. δe is equal to 30.

|   |A  |  C  |  G  |  T  |
| :---: | :---: | :---: | :---: | :--|
|A | 0 |  110 | 48  | 94 |
|C | 110| 0   | 118 | 48 |
|G | 48 |118 | 0  |  110 |
|T | 94 | 48  | 110 |  0 |

**Program Input:**

1. 2 strings that need to be aligned, should be generated from the string generator mentioned above.
2. Gap penalty (δe).
3. Mismatch penalty (αpq).

**Program Output:**

1. The first 50 elements and the last 50 elements of the actual alignment.
2. The time it took to complete the entire solution.
3. Memory required.

**Plots:**

1. CPU time vs problem size for the two solutions.
2. Memory usage vs problem size for the two solutions.
