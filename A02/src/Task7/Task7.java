package src.Task7;

public class Task7 {
    public static void main(String[] args) {
        testHuffmanConstruction();
        testGetHuffmanString();
        testGetHuffmanString_NotFound();
        testGetHuffmanString_SingleCharacter();
        testGetHuffmanString_RepeatedCharacters();
        testGetHuffmanString_fromFilePath();
    }

    private static void testHuffmanConstruction() {
        Huffman huffman = new Huffman("abacabad");
        assert huffman.root() != null;
        assert huffman.root().frequency == 8;
    }

    private static void testGetHuffmanString() {
        Huffman huffman = new Huffman("abacabad");
        assert huffman.getHuffmanString('a').equals("0");
        assert huffman.getHuffmanString('b').equals("10");
        assert huffman.getHuffmanString('c').equals("11");
        assert huffman.getHuffmanString('d').equals("111");
    }

    private static void testGetHuffmanString_NotFound() {
        Huffman huffman = new Huffman("abacabad");
        assert huffman.getHuffmanString('e') == null;
    }


    private static void testGetHuffmanString_SingleCharacter() {
        Huffman huffman = new Huffman("a");
        HuffNode root = huffman.root();
        assert root != null;
        assert root.frequency == 1;
        assert huffman.getHuffmanString('a').equals("");
    }

    private static void testGetHuffmanString_RepeatedCharacters() {
        Huffman huffman = new Huffman("aaaa");
        HuffNode root = huffman.root();
        assert root != null;
        assert root.frequency == 4;
        assert huffman.getHuffmanString('a').equals("0");
    }

    private static void testGetHuffmanString_fromFilePath() {
        Huffman huffman = new Huffman();
        huffman.fromFilePath("src/myFile.dat");
        assert huffman.root()!= null;
        String a = huffman.getHuffmanString('a');
        assert a.equals("0111") : "A not encoded as '0111', instead got " + a;

        String b = huffman.getHuffmanString('b');
        assert b.equals("00") : "B not encoded as '00', instead got " + b;

        String c = huffman.getHuffmanString('c');
        assert c.equals("0101") : "C not encoded as '0101', instead got " + c;

        String d = huffman.getHuffmanString('d');
        assert d.equals("01000") : "D not encoded as '01000', instead got " + d;

        String e = huffman.getHuffmanString('e');
        assert e.equals("1") : "E not encoded as '1', instead got " + e;

        String f = huffman.getHuffmanString('f');
        assert f.equals("01001") : "F not encoded as '01001', instead got " + f;
    }
}