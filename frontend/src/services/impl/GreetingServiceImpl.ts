import {injectable} from "inversify";
import type {GreetingService} from "../GreetingService.ts";


@injectable()
export class GreetingServiceImpl implements GreetingService {


    greet(name: string): string {
        return `こんにちは、${name}さん！DIコンテナから呼び出されました。`;
    }

}