package sample;

class DeviceInfo {
    private static DeviceInfo deviceInfo;
    private DeviceInfo() { } // private 클래스로 이 클래스의 생성자가 외부에서 호출되는 것을 막는다.
    public static DeviceInfo getInstance() { // 이 클래스의 클래스 정적 변수를 반환하는 정적 메소드
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();
        }
        return deviceInfo;
    }
}
