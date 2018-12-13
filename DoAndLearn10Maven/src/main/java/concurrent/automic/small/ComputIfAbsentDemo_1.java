package concurrent.automic.small;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import sun.net.www.content.audio.x_aiff;

//import concurrent.automic.Count;

public class ComputIfAbsentDemo_1 {
	private static ConcurrentHashMap<Integer, Integer> rights = new ConcurrentHashMap<>();
	private static AtomicInteger gener = new AtomicInteger(1);
	private static AtomicInteger gener2 = new AtomicInteger(1);
	private static ConcurrentHashMap<Integer, Integer> counts = new ConcurrentHashMap<>();
	static final int max = 10;
	static Set<GuildVo> result = Collections.newSetFromMap(new HashMap<>());

	static class Rest {
		int count;
		List<Req> reqs = new CopyOnWriteArrayList<>();
		int conCount = 2;
	}

	public static void main(String[] args) throws InterruptedException {
		final Rest re = new Rest();
		while (re.count <= 10) {
			if (result.size() > 0) {
				reset();
			}
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
			init(1000, 10);
			Integer[] keys = guildCache.keySet().toArray(new Integer[0]);
			Random random = new Random();
			Integer randomKey = keys[random.nextInt(keys.length)];
			GuildVo gvo = guildCache.get(randomKey);
			final int g1 = gvo.guildId;
//			gvo.rights.put(10000, 101);
//			gvo.rights.put(10001, 101);
//			gvo.rights.put(10002, 101);
//			gvo.rights.put(10003, 101);
//			gvo.rights.put(10004, 101);
//			gvo.rights.put(10005, 101);
//			gvo.rights.put(10006, 101);
//			gvo.rights.put(10007, 101);
//			gvo.rights.put(10008, 101);
			final Rest cou = new Rest();
			re.conCount = 1;
			reqCache.forEach((k, v) -> {
				// 引用cou来存储 vo,并让两任务同时开启
				List<Req> reqs = re.reqs;
				if (v.gid == g1 && reqs.size() < re.conCount) {
					reqs.add(v);
				}
				if (reqs.size() >= re.conCount) {
					reqs.forEach( x -> {
						fixedThreadPool.submit(new ConJoinTask(x));
					});
					reqs.clear();
					/*
					 * try { if (res1.get() != null && res2.get() != null) {
					 * result.forEach(x -> { re.count = x.rights.size();
					 * System.out.println(x.rights.size() + " "); }); } } catch
					 * (InterruptedException | ExecutionException e) {
					 * e.printStackTrace(); }
					 */
				}
			});

			fixedThreadPool.shutdown();
			fixedThreadPool.awaitTermination(1, TimeUnit.HOURS);
			result.forEach(x -> {
				re.count = x.rights.size();
				System.out.print("gid:" + x.guildId + " size:" + x.rights.size() + " ");
				System.out.println();
			});

		}
		System.exit(0);

	}

	private static void showGuild(GuildVo gvo) {
		Map<Integer, Integer> rights = gvo.rights;
		int gid = gvo.guildId;
		System.out.println(gvo);
		((ConcurrentHashMap<Integer, Integer>) rights).forEach((pid, v) -> {
			System.out.println(playerCache.get(pid));
			System.out.println(memCache.get(pid));
		});
	}

	private static void reset() {
		rights.clear();
		PVo.in.set(1);
		MemVo.in.set(1);
		GuildVo.in.set(1);
		MemVo.in.set(1);
		Req.in.set(1);
		result.clear();
		playerCache.clear();
		memCache.clear();
		reqCache.clear();
		guildCache.clear();
	}

	private static void dangerJoin() throws InterruptedException {
		/*
		 * Thread t1 = new Thread(new DangerJoinTask()); Thread t2 = new
		 * Thread(new DangerJoinTask()); t2.start(); t1.start();
		 * Thread.sleep(5000); System.out.println(rights.size());
		 * rights.clear();
		 */
	}

	private static void conJoin() throws InterruptedException {
		/*
		 * Thread t3 = new Thread(new ConJoinTask()); Thread t4 = new Thread(new
		 * ConJoinTask()); t3.start(); t4.start(); Thread.sleep(3000);
		 * System.out.println(rights);
		 */
	}

	static class ConGoodsTask implements Runnable {
		@Override
		public void run() {
			while (counts.get(1) > 0) {
				int id = gener2.getAndIncrement();
				System.out.println("id:" + id + "打算消耗一个物品");
				int count = counts.get(1);
				if (count > 0) {
					counts.computeIfPresent(1, (k, v) -> {
						try {
							Thread.sleep(200);
							if (v-- > 0) {
								System.out.println("id" + id + ":ok");
								return v;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					});
					try {
						Thread.sleep(200);
						count = counts.get(1);
						if (count <= 0) {
							break;
						}
						counts.put(1, count - 1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("id" + id + ":ok");
				}
			}
		}
	}

	static class SynGoodsTask implements Runnable {
		@Override
		public void run() {
			while (counts.get(1) > 0) {
				int id = gener2.getAndIncrement();
				System.out.println("id:" + id + "打算消耗一个物品");
				int count = counts.get(1);
				if (count > 0) {
					synchronized (counts) {
						try {
							Thread.sleep(200);
							count = counts.get(1);
							if (count <= 0) {
								break;
							}
							counts.put(1, count - 1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("id" + id + ":ok");
					}
				}
			}
		}
	}

	public static void init(int count, int guild_count) {
		for (int i = 0; i < guild_count; i++) {
			GuildVo g = new GuildVo();
			guildCache.put(g.guildId, g);
		}
		for (int i = 0; i < count; i++) {
			PVo p = new PVo();
			dao.put(p.id, p);
			if (i % 2 == 0) {
				playerCache.put(p.id, p);
				if (i % 8 == 0) {
					Req req = p.generReq();
					reqCache.put(req.id, req);
				}
			}
		}
	}

	static class Req {
		static AtomicIntegerFieldUpdater<Req> updater = AtomicIntegerFieldUpdater.newUpdater(Req.class, "state");
		public static final int NOR = 1;
		public static final int ACCEPT = 2;
		public static final int REFUSE = 3;

		volatile int state = NOR;
		static AtomicInteger in = new AtomicInteger(1);
		int pid;
		int gid;
		int id;

		Req(int pid, int gid) {
			this.pid = pid;
			this.gid = gid;
			id = in.getAndIncrement();
		}
	}

	static class MemVo {
		static AtomicInteger in = new AtomicInteger(1);
		int guildId;
		int pid;
		int id;

		@Override
		public String toString() {
			return "MemVo{" + "guildId=" + guildId + ", pid=" + pid + ", id=" + id + '}';
		}

		MemVo(int gid, int pid) {
			guildId = gid;
			this.pid = pid;
			id = in.getAndIncrement();
		}
	}

	static class GuildVo {
		static AtomicInteger in = new AtomicInteger(1);
		int guildId;
		ConcurrentHashMap<Integer, Integer> rights = new ConcurrentHashMap<Integer, Integer>();

		@Override
		public String toString() {
			return "GuildVo{" + "guildId=" + guildId + ", rights=" + rights + '}';
		}

		GuildVo() {
			guildId = in.getAndIncrement();
		}
	}

	static Random ra = new Random();

	static class PVo {
		static AtomicInteger in = new AtomicInteger(1);
		int id;
		int guildId;

		@Override
		public String toString() {
			return "PVo{" + "id=" + id + ", guildId=" + guildId + '}';
		}

		PVo() {
			guildId = -1;
			id = in.getAndIncrement();
		}

		Req generReq() {
			int size = guildCache.size();
			int gid = ra.nextInt(size) + 1;
			Req req = new Req(id, gid);
			// reqCache.put(req.id, req);
			return req;
		}
	}

	static Map<Integer, PVo> playerCache = new ConcurrentHashMap<>();
	static Map<Integer, Req> reqCache = new ConcurrentHashMap<Integer, Req>();
	static Map<Integer, GuildVo> guildCache = new ConcurrentHashMap<Integer, GuildVo>();
	static Map<Integer, MemVo> memCache = new ConcurrentHashMap<Integer, MemVo>();
	static Map<Integer, PVo> dao = new ConcurrentHashMap<>();

	static class ConJoinTask implements Callable<Boolean> {
		private Req req;

		public ConJoinTask(Req req) {
			this.req = req;
		}

		private void updateMemVoIfNullCreate(MemVo vo, int pid, int gid) {
			if (null == vo) {
				vo = new MemVo(gid, pid);
			} else {
				vo.guildId = gid;
			}
		}

		private void updatePvoIfNullCache(int pid, int gid) {
			PVo pvo = playerCache.get(pid);
			if (null == pvo)
				pvo = dao.get(pid);
			pvo.guildId = gid;
			// 这里应该追加playerInfo的信息更改
		}

		@Override
		public Boolean call() {
			int pid = req.pid;
			int gid = req.gid;
			System.out.println("发现了申请 req[p" + pid + " ,g" + gid + "]");
			PVo pVo = playerCache.get(pid);
			if (pVo == null || pVo.guildId != -1) {
				// throw new RuntimeException("没有该玩家或者已加入公会");
				System.out.println("pid" + pid + "没有该玩家或者已加入公会");
				return false;
			}
			GuildVo gvo = guildCache.get(gid);
			if (null == gvo) {
//				throw new RuntimeException("公会数据异常");
				System.out.println("gid" + gid + "公会数据异常");
				return false;
			}
			Map<Integer, Integer> rights = gvo.rights;
			if (rights.size() >= 10) {
				// throw new RuntimeException("公会" + gvo.guildId + "已满");
				System.out.println("公会" + gvo.guildId + "已满");
				return false;
			}
			rights.computeIfAbsent(pid, x -> {
				if (rights.size() >= 10) {
					// throw new RuntimeException("公会" + gvo.guildId +
					// "已满");
					System.out.println("公会" + gvo.guildId + "已满");
					return null;
				}
				if (req.updater.compareAndSet(req, Req.NOR, Req.ACCEPT)) {
					MemVo mvo = memCache.get(req.pid);
					updateMemVoIfNullCreate(mvo, pid, gid);// 这里没在做memvo中gid的验证
					reqCache.remove(req);
					Thread.yield();
					updatePvoIfNullCache(pid, gid);
					System.out.println("申请成功  req[p" + pid + " ,g" + gid + "]");
					result.add(gvo);
					return 101;
				} else {
					if (req.updater.compareAndSet(req, Req.NOR, Req.REFUSE)) {
						reqCache.remove(req);
						System.out.println("被拒绝  req[p" + pid + " ,g" + gid + "]");
					}
					return null;
				}
			});
			if (rights.containsKey(pid))
				return true;
			return false;
		}

	}

	static class Con2JoinTask implements Callable<Boolean> {
		private Req req;

		public Con2JoinTask(Req req) {
			this.req = req;
		}

		private void updateMemVoIfNullCreate(MemVo vo, int pid, int gid) {
			if (null == vo) {
				vo = new MemVo(gid, pid);
			} else {
				vo.guildId = gid;
			}
		}

		private void updatePvoIfNullCache(int pid, int gid) {
			PVo pvo = playerCache.get(pid);
			if (null == pvo)
				pvo = dao.get(pid);
			pvo.guildId = gid;
			// 这里应该追加playerInfo的信息更改
		}

		@Override
		public Boolean call() {
			int pid = req.pid;
			int gid = req.gid;
			System.out.println("发现了申请 req[p" + pid + " ,g" + gid + "]");
			PVo pVo = playerCache.get(pid);
			if (pVo == null || pVo.guildId != -1) {
				// throw new RuntimeException("没有该玩家或者已加入公会");
				System.out.println("pid" + pid + "没有该玩家或者已加入公会");
				return false;
			}
			GuildVo gvo = guildCache.get(gid);
			if (null == gvo)
				throw new RuntimeException("公会数据异常");
			Map<Integer, Integer> rights = gvo.rights;
			if (rights.size() >= 10)
				// throw new RuntimeException("公会" + gvo.guildId + "已满");
				System.out.println("公会" + gvo.guildId + "已满");
			rights.computeIfAbsent(pid, x -> {
				if (req.updater.compareAndSet(req, Req.NOR, Req.ACCEPT)) {
					if (gvo.rights.size() >= 10)
						// throw new RuntimeException("公会" + gvo.guildId +
						// "已满");
						System.out.println("公会" + gvo.guildId + "已满");
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
					MemVo mvo = memCache.get(req.pid);
					updateMemVoIfNullCreate(mvo, pid, gid);// 这里没在做memvo中gid的验证
					reqCache.remove(req);
					Thread.yield();
					updatePvoIfNullCache(pid, gid);
					System.out.println("申请成功  req[p" + pid + " ,g" + gid + "]");
					result.add(gvo);
					return 101;
				} else {
					if (req.updater.compareAndSet(req, Req.NOR, Req.REFUSE)) {
						reqCache.remove(req);
						System.out.println("被拒绝  req[p" + pid + " ,g" + gid + "]");
					}
					return null;
				}
			});
			if (rights.containsKey(pid))
				return true;
			return false;
		}

	}

	static class DangerJoinTask implements Runnable {
		@Override
		public void run() {
			while (rights.size() < max) {
				int id = gener.getAndAdd(1);
				System.out.println("准备假如公会 id = " + id);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rights.put(id, 101);
			}
		}
	}
}
