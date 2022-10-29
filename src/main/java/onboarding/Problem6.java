package onboarding;

import java.util.*;

public class Problem6 {
    public static List<String> solution(List<List<String>> forms) {
        HashMap<String, String> map = new HashMap<>(); //닉네임 중복 체크를 위해 닉네임의 파편들을 이메일주소와 함께 저장
        HashSet<String> set = new HashSet<>(); //중복되지 않도록 이메일을 저장

        for(int i = 0; i < forms.size(); i++){
            String nickname = forms.get(i).get(1);
            if(nickname.length() == 1){
                continue;
            }
            //중복 닉네임이 될 수 있는 단어들(part)을 추출
            for(int j = 0; j < nickname.length() - 1; j++){
                for(int k = j+1; k < nickname.length(); k++){
                    String str = nickname.substring(j, k+1);
                    //map에 저장된 단어가 사용자의 닉네임에 들어가는지 체크하는 기능
                    if(map.containsKey(str)){
                        //이미 map에 있던 단어는 중복 닉네임이 되므로 이메일을 set에 추가
                        set.add(forms.get(i).get(0));
                        set.add(map.get(str));
                    }else{
                        //처음 발견된 단어는 map에 <단어, 이메일>로 저장
                        map.put(str, forms.get(i).get(0));
                    }
                }
            }

        }
        //set을 list로 변경하여 같은 글자가 연속적으로 포함되는 닉네임을 작성한 지원자의 메일 목록을 반환하는 기능
        List<String> answer = new ArrayList<>(set); //set -> list
        Collections.sort(answer);
        return answer;
    }

    private boolean checkNicknameDuplication(String nickname, List<String> part){
        for(int i = 0; i < part.size(); i++){
            if(nickname.contains(part.get(i))){
                return true;
            }
        }
        return false;
    }

    private HashSet<String> extractNicknameParts(String nickname){
        HashSet<String> part = new HashSet<>();
        for(int j = 0; j < nickname.length() - 1; j++){
            for(int k = j+1; k < nickname.length(); k++){
                String str = nickname.substring(j, k+1);
                part.add(str);
            }
        }
        return part;
    }

}
