package spell;

public class Dictionary implements ITrie {

  private int nodeCount = 0;
  private int wordCount = 0;
  private TrieNode root = new TrieNode();



  @Override
  public String toString()
  {
    //toString_Helper(root);
    return "hi";
  }

  private void toString_Helper(TrieNode n)
  {
    if (n.getValue() > 0)
    {

    }

  }



  ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void add(String word)
  {
    TrieNode currentNode = root;
    for (int i = 0 ; i < word.length() ; i++)
    {
      currentNode.getChildren();
    }
  }

  @Override
  public INode find(String word)
  {
    return null;
  }

  @Override
  public int getWordCount() {
    return wordCount;
  }

  @Override
  public int getNodeCount() {
    return nodeCount;
  }
}
