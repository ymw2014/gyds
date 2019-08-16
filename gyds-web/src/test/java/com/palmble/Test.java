package com.palmble;

import java.math.BigDecimal;

import com.fly.utils.MathUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={WebMain.class})
public class Test {
//    @Autowired
//    private OrderController orderController;
//private EmojiConverter emojiConverter = EmojiConverter.getInstance();
    @org.junit.Test
    public void test1(){
    	BigDecimal teamCount = MathUtils.getBigDecimal(10);
		BigDecimal roadCount = MathUtils.getBigDecimal(100);
		BigDecimal result5 = teamCount.divide(roadCount,2,BigDecimal.ROUND_HALF_UP);
    	
        String str = "&#128581; &#128582; &#128145;An &#128515;&#128512;awesome &#128515;&#128515;string with a few &#128515;&#128521;emojis!";
//        String alias = this.emojiConverter.toUnicode(str);
        System.out.println(result5);
        System.out.println("EmojiConverterTest.testToAlias()=====>");
      //  System.out.println(emojiConverter.toHtml(str));
    }
}
