package spell;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector
{

  private Trie myTrie;

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException
  {
    myTrie.add(dictionaryFileName);
  }

  @Override
  public String suggestSimilarWord(String inputWord)
  {


    return "hi";
  }
}
