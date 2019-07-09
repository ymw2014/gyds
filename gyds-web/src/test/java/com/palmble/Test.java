package com.palmble;




//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={WebMain.class})
public class Test {
//    @Autowired
//    private OrderController orderController;
//private EmojiConverter emojiConverter = EmojiConverter.getInstance();
    @org.junit.Test
    public void test1(){
        String str = "&#128581; &#128582; &#128145;An &#128515;&#128512;awesome &#128515;&#128515;string with a few &#128515;&#128521;emojis!";
//        String alias = this.emojiConverter.toUnicode(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToAlias()=====>");
      //  System.out.println(emojiConverter.toHtml(str));
    }
}
