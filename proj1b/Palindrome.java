public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();

        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }

        return result;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }

        Deque<Character> a = wordToDeque(word);
        String wordReverse = "";

        for (int i = 0; i < word.length(); i++) {
            wordReverse += a.removeLast();
        }

        return wordReverse.equals(word);
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        String wordReverse = "";

        Deque<Character> dq = wordToDeque(word);

        for (int i = 0; i < word.length(); i++) {
            wordReverse += dq.removeLast();
        }

        if (word.length() % 2 != 0) {
            for (int i = 0; i < word.length(); i++) {
                if (i == word.length() / 2) {
                    continue;
                }
                if (!cc.equalChars(wordReverse.charAt(i), word.charAt(i))) {
                    return false;
                }
            }
        } else {
                for (int i = 0; i < word.length(); i++) {
                    if (!cc.equalChars(wordReverse.charAt(i), word.charAt(i))) {
                        return false;
                    }
                }
            }

            return true;
        }

}

