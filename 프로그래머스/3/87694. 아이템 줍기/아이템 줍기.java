import java.util.*;

class Solution {
    static class Point
    {
        public int x;
        public int y;
        //int dist;
        
        Point (int x, int y)
        {
            this.x = x;
            this.y = y;
            //this.dist = dist; // 추가
        }
    }
    static int [][] maps; // 1과 0으로 갈 수 있는 위치 설정
    static int [][] m_visited; // 방문 여부 체크
    
    static Queue <Point> m_queue; // 현재 위치에서 이동 가능한 위치 저장
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        
        int answer = 0;
        int map_size = 0;
        
        m_queue = new LinkedList<>();
        
        // 1. 제일 큰 숫자 찾기 
        for(int i=0; i<rectangle.length; i++)
        {
            for(int j=0; j<rectangle[i].length; j++)
            {
                if(rectangle[i][j] > map_size)
                {
                    map_size = rectangle[i][j];
                }
            }
        }
        
        map_size = map_size*2;
        
        // 2. 제일 큰 숫자 크기로 형성된 2차 배열 만들기
        maps = new int[map_size+1][map_size+1];
        m_visited = new int[map_size+1][map_size+1];
        
        // 3. 맵에 갈수 있는 길에 1로 표시 1차 => 2배로 그려주기 (꺾어지는 부분때문에)
        for(int i=0; i<rectangle.length; i++)
        {
            int left = rectangle[i][0]*2;
            int bottom = rectangle[i][1]*2;
            int right = rectangle[i][2]*2;
            int top = rectangle[i][3]*2;
            
            // 왼쪽선 표시 => x 고정, y는 bottom ~ top까지
            for(int y = bottom; y<= top;  y++)
            {
                maps[y][left] = 1;
            }
            
            // 아래 선 => y는 고정, x는 left ~ right까지
            for(int x = left; x<= right;  x++)
            {
                maps[bottom][x] = 1;
            }
            
            // 오른쪽 선 => x 고정, y는 bottom ~ top까지
            for(int y = bottom; y<= top;  y++)
            {
                maps[y][right] = 1;
            }
            
            // 위 선 => y는 고정, x는 left ~ right까지
            for(int x = left; x<= right;  x++)
            {
                maps[top][x] = 1;
            }
        }
        
        // 2. 중간에 있는 1들 삭제하기...
        for(int i=0; i<rectangle.length; i++)
        {
            int left = rectangle[i][0]*2;
            int bottom = rectangle[i][1]*2;
            int right = rectangle[i][2]*2;
            int top = rectangle[i][3]*2;
            
            for(int a = bottom+1; a<top; a++)
            {
                for(int b = left+1; b<right; b++)
                {
                    if(maps[a][b] == 1)
                    {
                        maps[a][b] = 0;
                    }
                }
            }
        }
        
        
        int res = 0;
        
        m_queue.add(new Point(characterX*2, characterY*2));
        m_visited[characterY*2][characterX*2] = 1;
        
        boolean bStop = false;
        
        while(!m_queue.isEmpty() && bStop==false)
        {
            int size = m_queue.size();
            res++;
            for(int i=0; i<size; i++)   
            {
                // map의 현재 위치에서 이동 가능한 항목을 모두 큐에 넣어준다.
                Point pt = m_queue.poll();
                
                if(itemX*2 == pt.x && itemY*2 == pt.y)
                {
                    answer=res/2;
                    bStop = true;
                    break;
                }
                
                // 왼쪽
                if(pt.x > 1)
                {
                    if(maps[pt.y][pt.x-1] == 1)
                    {
                        if(m_visited[pt.y][pt.x-1] == 0)
                        {
                            m_visited[pt.y][pt.x-1] = 1;
                            m_queue.add(new Point(pt.x-1, pt.y));
                        }
                        
                    }
                }
            
                // 위
                if(pt.y > 1)
                {
                    if(maps[pt.y-1][pt.x] == 1)
                    {
                        if(m_visited[pt.y-1][pt.x] == 0)
                        {
                            m_visited[pt.y-1][pt.x] = 1;
                            m_queue.add(new Point(pt.x, pt.y-1));
                        }
                    }
                    
                }
            
                // 오른쪽
                if(pt.x < map_size)
                {
                    if(maps[pt.y][pt.x+1] == 1)
                    {
                        if(m_visited[pt.y][pt.x+1] == 0)
                        {
                            m_visited[pt.y][pt.x+1] = 1;
                            m_queue.add(new Point(pt.x+1, pt.y));
                        }
                    }
                    
                }
            
                // 아래
                if(pt.y < map_size)
                {
                    if(maps[pt.y+1][pt.x] == 1)
                    {
                        if(m_visited[pt.y+1][pt.x] == 0)
                        {
                            m_visited[pt.y+1][pt.x] = 1;
                            m_queue.add(new Point(pt.x, pt.y+1));
                        }
                    }
                }
            }
        }
        
        // 지도 확인 코드
        for(int i=0; i<=map_size; i++)
        {
            //System.out.print(i+":");
            for(int j=0; j<=map_size; j++)
            {
                // 이동 가능한 경우
                if(maps[i][j] == 1)
                {
                    System.out.print("1 ");
                }
                // 불가능한 경우
                else
                {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        
        
        return answer;
    }
}