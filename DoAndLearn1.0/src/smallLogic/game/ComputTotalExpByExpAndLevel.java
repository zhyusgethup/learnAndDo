package smallLogic.game;
/**
 * 计算公会排行需要一个总经验,由于当前经验是升1级就清0,所以要联合等级计算
 * 我正在犹豫该新设字段还是该每次计算,看看别人传递给排行系统的总经验数字的算法.
 * @author Zeng Haoyu
 * 2018年12月4日 下午3:09:40
 */
public class ComputTotalExpByExpAndLevel {
	private int level;
	private int exp;
	/**
	 * 由于公会虽然每一级的升级经验都在变化,但是9-10级也不会消耗1000万经验,所以.
	 * 666666
	 * @return
	 */
	public long getRankVal(){
    	return level*10_000_000+exp;
    }

}
