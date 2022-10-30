package onboarding;

import java.util.*;

public class Problem7 {
    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = new ArrayList<>();
        HashMap<String, Integer> scoreList = new HashMap<>(); //사용자 친구추천 후보들

        //사용자와 이미 친구인 사람들 목록 만들기
        HashSet<String> friendList = makeFriendList(friends, user);

        //함께아는 친구 점수 계산하는 기능
        for(int i = 0; i < friends.size(); i++){
            List<String> pair = friends.get(i);

            //사용자는 제외
            if(pair.get(0).equals(user) || pair.get(1).equals(user)){
                continue;
            }

            //점수계산
            if(friendList.contains(pair.get(0))){
                String friendOfFriend = pair.get(1);
                scoreList = accumulateRecommendedScore(scoreList, friendOfFriend, 10);
            }else if(friendList.contains(pair.get(1))){
                String friendOfFriend = pair.get(0);
                scoreList = accumulateRecommendedScore(scoreList, friendOfFriend, 10);
            }
        }

        //visitor 점수 계산하는 기능
        for(int i = 0; i < visitors.size(); i++){
            String visitor = visitors.get(i);
            if(friendList.contains(visitor)){
                //이미 사용자와 친구인 경우
                continue;
            }
            //사용자와 아직 친구가 아닌 경우엔 점수 추가
            scoreList = accumulateRecommendedScore(scoreList, visitor, 1);
        }

        //점수높은 5명으로 추천 리스트 만들기
        answer = makeRecommendedFriendList(scoreList);

        return answer;
    }

    private static  HashSet<String> makeFriendList(List<List<String>> friends, String user){
        HashSet<String> set = new HashSet<>(); //사용자와 이미 친구인 사람 목록에 중복으로 추가하지 않기 위함
        for(int i = 0; i < friends.size(); i++){
            if(friends.get(i).get(0).equals(user)){
                set.add(friends.get(i).get(1));
            }else if(friends.get(i).get(1).equals(user)){
                set.add(friends.get(i).get(0));
            }
        }
        return set;
    }

    private static HashMap<String, Integer> accumulateRecommendedScore(HashMap<String, Integer> map, String recommendedFriend, int score){
        if(map.containsKey(recommendedFriend)){
            int sum = map.get(recommendedFriend) + score;
            map.put(recommendedFriend, sum);
        }else{
            map.put(recommendedFriend, score);
        }
        return map;
    }

    private static List<String> makeRecommendedFriendList(HashMap<String, Integer> map){
        List<String> result = new ArrayList<>();

        //점수 내림차순 정렬
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                int res = b.getValue().compareTo(a.getValue());
                if(res == 0){
                    res = a.getKey().compareTo(b.getKey());
                }
                return res;
            }
        });

        //추천리스트 만들기
        for(int i = 0; i < list.size(); i++){
            if(i == 5){
                break;
            }
            if(list.get(i).getValue() == 0){
                //점수가 0이면 리스트에 넣지 않음
                break;
            }else{
                result.add(list.get(i).getKey());
            }
        }

        return result;
    }

}
