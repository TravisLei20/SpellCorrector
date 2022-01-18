package spell;

import java.lang.StringBuilder;

public class Trie implements ITrie {

  private int nodeCount;
  private int wordCount;
  private INode root;

  private static int NUM_OF_CHILDREN = 26;

  public Trie()
  {
    nodeCount = 1;
    wordCount = 0;
    root = new Node();
  }

  @Override
  public String toString()
  {
    StringBuilder currentWord = new StringBuilder();
    StringBuilder output = new StringBuilder();

    toString_Helper(root, currentWord, output);

    return output.toString();
  }

  private void toString_Helper(INode n, StringBuilder currentWord, StringBuilder output)
  {
    if (n.getValue() > 0)
    {
      output.append(currentWord.toString());
      output.append("\n");
    }

    int numOfChildren = 26;

    for (int i = 0 ; i < numOfChildren ; i++)
    {
      INode child = n.getChildren()[i];
      if (child != null)
      {
        currentWord.append((char)('a' + i));
        toString_Helper( (Node)child , currentWord, output);

        currentWord.deleteCharAt(currentWord.length() - 1);
      }
    }

  }


  @Override
  public void add(String word)
  {
    INode currentNode = root;

    for (int i = 0 ; i < word.length() ; i++)
    {
      int index = (word.charAt(i) - 'a');
      if (currentNode.getChildren()[index] == null)
      {
        currentNode.getChildren()[index] = new Node();
        currentNode = currentNode.getChildren()[index];
        nodeCount++;
      }
      else
      {
        currentNode = currentNode.getChildren()[index];
      }

      if (i == (word.length() - 1))
      {
        if (currentNode.getValue() == 0)
        {
          ++wordCount;
        }
        currentNode.incrementValue();
      }

    }
  }

  @Override
  public INode find(String word)
  {
    int indexStart = 0;
    return find_Helper(root.getChildren()[ (word.charAt(0) - 'a') ], word, indexStart);
  }

  private INode find_Helper(INode currentNode, String word, int currIndex)
  {
    if (currentNode == null)
    {
      return null;
    }

    if (currIndex == (word.length() - 1))
    {
      if (currentNode.getValue() > 0)
      {
        return currentNode;
      }
      else
      {
        return null;
      }
    }
    else
    {
      if (currentNode.getChildren()[ (word.charAt(++currIndex) - 'a') ] == null)
      {
        return null;
      }
    }

    return find_Helper(currentNode.getChildren()[ (word.charAt(currIndex) - 'a') ], word, currIndex);
  }

  @Override
  public int getWordCount()
  {
    return wordCount;
  }

  @Override
  public int getNodeCount()
  {
    return nodeCount;
  }

  @Override
  public int hashCode()
  {
    int rootIndexSum = 0;

    for (int i=0; i < NUM_OF_CHILDREN; i++)
    {
      if (root.getChildren()[i] != null)
      {
        if ((i % 2) == 0)
        {
          rootIndexSum += i;
        }
        else
        {
          rootIndexSum *= i;
        }
      }
      else
      {
        if ((i % 2) == 0)
        {
          rootIndexSum *= i;
        }
        else
        {
          rootIndexSum += i;
        }
      }
    }

    return nodeCount * wordCount * rootIndexSum;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == null && obj == null)
    {
      return true;
    }

    if(obj == null)
    {
      return false;
    }

    if(obj == this)
    {
      return true;
    }

    if (this.getClass() != obj.getClass())
    {
      return false;
    }

    Trie compareTrie = (Trie)obj;

    if (this.nodeCount != compareTrie.getNodeCount())
    {
      return false;
    }

    if (this.wordCount != compareTrie.getWordCount())
    {
      return false;
    }

    return equals_Helper(this.root , compareTrie.root);
  }

  private boolean equals_Helper(INode obj1, INode obj2)
  {
    if (obj1.getValue() != obj2.getValue())
    {
      return false;
    }

    for (int i=0; i < NUM_OF_CHILDREN; i++)
    {
      if ((obj1.getChildren()[i] == null && obj2.getChildren()[i] != null) || (obj1.getChildren()[i] != null && obj2.getChildren()[i] == null))
      {
        return false;
      }
    }

    for (int i=0; i < NUM_OF_CHILDREN; i++)
    {
      if (obj1.getChildren()[i] != null && obj2.getChildren()[i] != null)
      {
        if (!equals_Helper(obj1.getChildren()[i] , obj2.getChildren()[i]))
        {
          return false;
        }
      }
      else if (obj1.getChildren()[i] == null && obj2.getChildren()[i] == null)
      {}
      else
      {
        return false;
      }
    }

    return true;
  }


}
