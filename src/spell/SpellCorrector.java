package spell;

import java.io.IOException;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class SpellCorrector implements ISpellCorrector
{
  private Trie myTrie;

  HashSet<String> similarWords = new HashSet<>();
  HashSet<String> editedWords = new HashSet<>();

  private static int NUM_OF_CHILDREN = 26;

  public SpellCorrector()
  {
    myTrie = new Trie();
    similarWords.clear();
    editedWords.clear();
  }

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException
  {

    File file = new File(dictionaryFileName);
    Scanner scanner = new Scanner(file);

    while(scanner.hasNext())
    {
      myTrie.add(scanner.next().toLowerCase());
    }

  }

  @Override
  public String suggestSimilarWord(String inputWord)
  {
    inputWord = inputWord.toLowerCase();

    if (inputWord.length() < 1)
    {
      return null;
    }

    if (myTrie.find(inputWord) != null)
    {
      return inputWord;
    }

    similarWords.clear();
    editedWords.clear();

    alterationCharacterFunction(inputWord);
    transposeCharacterFunction(inputWord);
    deletionCharacterFunction(inputWord);
    insertionCharacterFunction(inputWord);

    if (!similarWords.isEmpty())
    {
      return distanceOne();
    }

    HashSet<String> useThisOneForTheForLoop = new HashSet<>(editedWords);
    editedWords.clear();

    for (String word : useThisOneForTheForLoop)
    {
      alterationCharacterFunction(word);
      transposeCharacterFunction(word);
      deletionCharacterFunction(word);
      insertionCharacterFunction(word);
    }

    if (!similarWords.isEmpty())
    {
      return distanceOne();
    }

    return null;
  }

  private String distanceOne()
  {
    HashSet<String> bestWords = new HashSet<>();
    int bestWordValue = 0;

    for (String word : similarWords)
    {
      if (myTrie.find(word).getValue() > bestWordValue)
      {
        bestWordValue = myTrie.find(word).getValue();
        bestWords.clear();
        bestWords.add(word);
      }
      else if (myTrie.find(word).getValue() == bestWordValue)
      {
        bestWords.add(word);
      }
    }

    if (bestWords.size() == 1)
    {
      String bestWord = bestWords.toString();
      return bestWord.substring(1,bestWord.length()-1);
    }
    else
    {
      List<String> alphabeticalList = new ArrayList<>(bestWords);
      Collections.sort(alphabeticalList);
      return alphabeticalList.get(0);
    }
  }

  private void alterationCharacterFunction(String word)
  {
    for (int i=0; i < word.length(); i++)
    {
      for (int j=0; j < NUM_OF_CHILDREN ; j++)
      {
        char[] newWord = word.toCharArray();
        newWord[i] = (char)('a'+j);
        if (myTrie.find(String.valueOf(newWord)) != null)
        {
          similarWords.add(String.valueOf(newWord));
        }
        else
        {
          editedWords.add(String.valueOf(newWord));
        }
      }
    }
  }

  public void transposeCharacterFunction(String word)
  {
    if (word.length() > 1)
    {
      for (int i=0; i < word.length()-1; i++)
      {
        char[] newWord = word.toCharArray();
        char temp = newWord[i];

        newWord[i] = newWord[i+1];
        newWord[i+1] = temp;

        if (myTrie.find(String.valueOf(newWord)) != null)
        {
          similarWords.add(String.valueOf(newWord));
        }
        else
        {
          editedWords.add(String.valueOf(newWord));
        }
      }
    }
  }

  public void deletionCharacterFunction(String word)
  {
    if (word.length() > 1)
    {
      for (int i=0; i < word.length(); i++)
      {
        StringBuilder newWord = new StringBuilder();
        for (int j=0; j < word.length(); j++)
        {
          if (j != i)
          {
            newWord.append(word.charAt(j));
          }
        }
        String theNewWord = newWord.toString();
        if (myTrie.find(theNewWord) != null)
        {
          similarWords.add(theNewWord);
        }
        else
        {
          editedWords.add(theNewWord);
        }
      }
    }
  }

  public void insertionCharacterFunction(String word)
  {
    for (int i=0; i < word.length()+1; i++)
    {
      String word1 = word.substring(0,i);
      String word2 = word.substring(i);

      for (int j=0; j < NUM_OF_CHILDREN ; j++)
      {
        String newWord = word1 + (char)('a'+j) + word2;

        if (myTrie.find(newWord) != null)
        {
          similarWords.add(newWord);
        }
        else
        {
          editedWords.add(newWord);
        }
      }
    }
  }
}
