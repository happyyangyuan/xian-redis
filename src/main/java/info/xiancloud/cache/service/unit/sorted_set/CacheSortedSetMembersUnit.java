package info.xiancloud.cache.service.unit.sorted_set;

import info.xiancloud.cache.redis.Redis;
import info.xiancloud.cache.service.CacheGroup;
import info.xiancloud.core.*;
import info.xiancloud.core.message.UnitRequest;
import info.xiancloud.core.message.UnitResponse;
import info.xiancloud.core.support.cache.CacheConfigBean;

import java.util.Set;

/**
 * Sorted Set Members
 * <p>
 * http://doc.redisfans.com/sorted_set/zrange.html
 *
 * @author John_zero, happyyangyuan
 */
public class CacheSortedSetMembersUnit implements Unit {

    @Override
    public String getName() {
        return "cacheSortedSetMembers";
    }

    @Override
    public Group getGroup() {
        return CacheGroup.singleton;
    }

    @Override
    public UnitMeta getMeta() {
        return UnitMeta.createWithDescription("Sorted Set Members").setPublic(false);
    }

    @Override
    public Input getInput() {
        return new Input()
                .add("key", String.class, "", REQUIRED)
                .add("cacheConfig", CacheConfigBean.class, "", NOT_REQUIRED);
    }

    @Override
    public void execute(UnitRequest msg, Handler<UnitResponse> handler) throws Exception {
        String key = msg.get("key", String.class);
        CacheConfigBean cacheConfigBean = msg.get("cacheConfig", CacheConfigBean.class);

        Set<String> members = Redis.call(cacheConfigBean, jedis -> jedis.zrange(key, 0, -1));
        handler.handle(UnitResponse.createSuccess(members));
    }

}
